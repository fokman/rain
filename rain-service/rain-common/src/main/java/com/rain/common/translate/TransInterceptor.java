package com.rain.common.translate;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.rain.common.core.Interceptor;
import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceRespose;
import com.rain.common.uitls.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransInterceptor implements Interceptor {
    private static final Logger logger = LoggerFactory.getLogger(TransInterceptor.class);
    private static final String PREFIX_LINKER = "$";

    @Override
    public boolean preHandle(Object iceCls, Method method, IceRequest iceRequest, IceRespose iceRespose) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void afterHandle(Object iceCls, Method method, IceRequest iceRequest, IceRespose iceRespose) {
        // @Trans(params = {"publish_user,org_user,nick_name", "replyUser,org_user,nick_name"})
        try{
            Trans trans = method.getAnnotation(Trans.class);
            if (trans != null) {
                if (trans.type() == TransType.QUERY) {//id to name 翻译
                    for (int i = 0; i < trans.params().length; i++) {
                        String pattern = trans.params()[i];
                        String[] pKeys = pattern.split("\\,");
                        if (pKeys != null && pKeys.length == 3) {
                            if (pKeys[1].endsWith("Status")) {
                                getStatus(iceRespose, pKeys[0], pKeys[1], pKeys[2]);
                            } else {
                                getRedis(iceRespose, pKeys[0], pKeys[1], pKeys[2]);
                            }
                        }
                    }
                } else { //基础数据id和name的删除
                    int len = trans.params().length;
                    if (len != 1) {
                        return;
                    }
                    String pattern = trans.params()[0];
                    String[] pKeys = pattern.split("\\,");
                    String id = pKeys[0];
                    String table = pKeys[1];
                    String name = pKeys[2];
                    String idValue = iceRequest.getAttr(id);
                    String nameValue = iceRequest.getAttr(name);
                    if (!StringUtils.isEmpty(idValue)) {
                        if (trans.type() == TransType.DEL) {
                            RedisUtils.remove(table + PREFIX_LINKER + idValue);
                        } else {//基础数据id和name的修改
                            if (!StringUtils.isEmpty(nameValue))
                                RedisUtils.set(table + PREFIX_LINKER + idValue, nameValue);
                        }
                    }
                }

            }
        }catch (Exception e){
            logger.error(e.getClass()+"\t"+e.getMessage()+"\t"+e.getStackTrace());
        }

    }

    private void getStatus(IceRespose iceRespose, String key, String service, String name) {
        if (iceRespose.getCode() == 0) {
            Object data = iceRespose.getData();
            if (data instanceof Map) {
                if (data != null && !((Map<String, Object>) data).isEmpty()) {
                    Map<String, Object> tmp = (Map<String, Object>) data;
                    String status = TransStartUp.getStatusmap().get(service + "_" + tmp.get("status"));
                    tmp.put(name, status);
                }
            } else if (data instanceof List) {
                ArrayList<Map<String, Object>> list = (ArrayList<Map<String, Object>>) data;
                for (Map<String, Object> map : list) {

                    String status = TransStartUp.getStatusmap().get(service + "_" + map.get("status"));
                    map.put(name, status);
                }
                int realTotal = iceRespose.getTotal();
                iceRespose.setData(list);
                iceRespose.setTotal(realTotal);
            }

        }
    }
    private boolean getTableRedis(String table){
    	return TransStartUp.putTableRedis(table);
    }
    private void getRedis(IceRespose iceRespose, String id, String table, String name) {
        if (iceRespose.getCode() == 0) {
            //如果缓存没数据，先加载
            if (!getTableRedis(table)) return ;
            Object data = iceRespose.getData();
            List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
            if (data instanceof List) {
                //RedisUtils.set(table, "1");
                List<?> list = (List<?>) data;
                int dlen = list.size();
                if (dlen<=0) return ;
                //List<String> ids = new ArrayList<String>();
                //                String ids = "";
                StringBuffer ids = new StringBuffer();
                for (int i = 0; i < dlen; i++) {
                    //putMapRedis(table,id,name,(Map)list.get(i));   				
                    //ids.add(getIdtoValue(id,table,(Map)list.get(i)));   		
                    //                    ids += getIdtoValue(id, table, (Map) list.get(i)) + ",";
                    ids.append(getIdtoValue(id, table, (Map) list.get(i))).append(",");
                }
                List<String> idValues = RedisUtils.gets(ids.toString().split("\\,"));
                int len = idValues.size();
                if (len!=dlen){
                	logger.warn("table: {}  Db data szie {} sizes unequal, Redis data szie: {}", table,dlen, len);
                	return ;
                }
                for (int i = 0; i < len; i++) {
                    Map<String, Object> rowMap = new HashMap<String, Object>();
                    rowMap.putAll((Map) list.get(i));
                    rowMap.put(name, idValues.get(i));
                    rows.add(rowMap);
                }
                int realTotal = iceRespose.getTotal();
                iceRespose.setData(rows);
                iceRespose.setTotal(realTotal);
                logger.info("table: {}  cache data szie: {}", table, len);
            } else if (data instanceof Map) {
                if (data != null && !((Map) data).isEmpty()) {
                    //RedisUtils.set(table, "1");
                    getMapRedis(id, table, name, (Map) data);
                }
            }
        }
    }

    private String getIdtoValue(String id, String table, Map<String, Object> data) {
        String keyId = "";
        if (data != null && !data.isEmpty()) {
            for (Entry<String, Object> entry : data.entrySet()) {
                String key = entry.getKey();
                if (StringUtils.isNotEmpty(entry.getKey()) && entry.getKey().equals(id)) {
                    keyId = String.valueOf(data.get(key));
                    break;
                }
            }
            /*Set set = data.keySet();
            for (Iterator iter = set.iterator(); iter.hasNext();) {
                String key = (String) iter.next();
                if (key.equals(id)) {
                    keyId = String.valueOf(data.get(key));
                }
            }*/
        }
        return table + PREFIX_LINKER + keyId;
    }

    private Map<String, Object> getMapRedis(String id, String table, String name, Map<String, Object> data) {
        Map<String, Object> rowMap = new HashMap<String, Object>();
        if (data != null && !data.isEmpty()) {
            Set set = data.keySet();
            String keyId = "";
            for (Iterator iter = set.iterator(); iter.hasNext();) {
                String key = (String) iter.next();
                if (key.equals(id)) {
                    keyId = String.valueOf(data.get(key));
                }
                String keyName = RedisUtils.get(table + PREFIX_LINKER + keyId);
                if (keyName != null) {
                    rowMap.put(name, keyName);
                }
            }
            for (Entry<String, Object> entry : rowMap.entrySet()) {
                data.put(entry.getKey(), entry.getValue());
            }
            return data;
        } else {
            return null;
        }
    }
}
