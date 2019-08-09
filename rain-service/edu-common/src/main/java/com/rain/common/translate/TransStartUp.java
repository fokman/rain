package com.rain.common.translate;

import com.alibaba.druid.util.StringUtils;
import com.rain.common.core.SoaManager;
import com.rain.common.ice.message.MsgRequest;
import com.rain.common.ice.model.IceRespose;
import com.rain.common.servcie.StartupService;
import com.rain.common.servcie.config.Startup;
import com.rain.common.uitls.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Startup
@Slf4j
public class TransStartUp implements StartupService {
    private static final String PREFIX_LINKER = "$";
    private static final ConcurrentHashMap<String, String> statusMap = new ConcurrentHashMap<String, String>();
    private static final ConcurrentHashMap<String, String> TableMap = new ConcurrentHashMap<String, String>();

    @Override
    public void startup(Properties properties) {
        log.info("TransStartUp-service-start");
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() throws Exception {
        String eduHome = AppUtils.getEduHome();
        String fileName = UrlUtils.joinUrl(eduHome, "config/idtoname.json");
        File file = new File(fileName);
        if (file.exists()) {
            String json = FileUtils.readFileToString(file, "UTF-8");
            Map<String, Object> map = (Map<String, Object>) JsonUtils.toMap(json);
            List<?> list = (List<?>) map.get("data");
            for (Object object : list) {
                Map<String, Object> rsMap = (Map<String, Object>) object;
                String service = (String) rsMap.get("service");
                String method = (String) rsMap.get("method");
                String table = (String) rsMap.get("table");
                String id = (String) rsMap.get("id");
                String name = (String) rsMap.get("name");
                String load = RedisUtils.get(table);
                if (StringUtils.isEmpty(load)) {
                    putRedis(service, method, table, id, name);
                }
                TableMap.put(table, service + "," + method + "," + id + "," + name);
                log.info("TransStartUp load data: {} {} {}", table, id, name);
            }
            List<Map<String, Object>> statusList = (List<Map<String, Object>>) map.get("status");
            if (statusList == null) return;
            for (Map<String, Object> status : statusList) {
                String service = (String) status.get("service");
                List<Map<String, Object>> innerList = (List<Map<String, Object>>) status.get("status");
                for (Map<String, Object> item : innerList) {
                    statusMap.put(service + "_" + item.get("status"), item.get("value").toString());
                }
            }
        }
    }

    public static void putRedis(String service, String method, String table, String id, String name) {
        MsgRequest msgRequest = new MsgRequest(service, method, null, null);
        try {
            IceRespose iceRespose = SoaManager.getInstance().doInvoke(msgRequest);
            if (iceRespose.getCode() == 0) {
                Object data = iceRespose.getData();
                if (data instanceof List) {
                    RedisUtils.set(table, "1");
                    List<?> list = (List<?>) data;
                    int len = list.size();
                    for (int i = 0; i < len; i++) {
                        putMapRedis(table, id, name, (Map) list.get(i));
                    }
                    log.info("table: {}  cache data szie: {}", table, len);
                } else if (data instanceof Map) {
                    if (data != null && !((Map) data).isEmpty()) {
                        RedisUtils.set(table, "1");
                        putMapRedis(table, id, name, (Map) data);
                    }
                }
            } else {
                //如果出异常,清除redis里面的table对于的key,这样,页面加载数据的时候,判断某个table没有被缓存进来,就会重新加载
                RedisUtils.remove(table);
            }
        } catch (Exception e) {
            RedisUtils.remove(table);
        }

    }

    public static void putMapRedis(String table, String id, String name, Map<String, Object> data) {
        if (data != null && !data.isEmpty()) {
            Set set = data.keySet();
            String keyId = "";
            String keyName = "";
            for (Iterator iter = set.iterator(); iter.hasNext(); ) {
                String key = (String) iter.next();
                if (key.equals(id)) {
                    keyId = String.valueOf(data.get(key));
                }
                if (key.equals(name)) {
                    keyName = String.valueOf(data.get(key));
                }
            }
            RedisUtils.set(table + PREFIX_LINKER + keyId, keyName);
        }
    }

    public static ConcurrentHashMap<String, String> getStatusmap() {
        return statusMap;
    }

    public static boolean putTableRedis(String table) {
        String load = RedisUtils.get(table);
        boolean isExist = true;
        if (load == null) {
            isExist = false;
            String pattern = TableMap.get(table);
            if (pattern == null) return isExist;
            String[] pKeys = pattern.split("\\,");
            if (pKeys != null && pKeys.length == 4) {
                putRedis(pKeys[0], pKeys[1], table, pKeys[2], pKeys[3]);
                isExist = true;
            }
        }
        return isExist;
    }
}
