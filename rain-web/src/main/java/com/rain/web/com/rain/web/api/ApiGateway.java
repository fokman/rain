package com.rain.web.com.rain.web.api;

import com.rain.common.ice.model.IceRequest;
import com.rain.common.ice.model.IceRespose;
import com.rain.common.ice.utils.IceClientUtils;
import com.rain.common.uitls.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@RestController
public class ApiGateway {

    @PostMapping("/api")
    public IceRespose apiExe(IceRequest iceRequest) throws IOException {
        long begin = System.currentTimeMillis();
/*        // 权限校验
        if (!checkPrivilege(iceRequest.getService(), iceRequest.getMethod())) {
            IceRespose iceRespose = new IceRespose();
            iceRespose.setCode(2, "操作不正确，对服务的操作权限不足!");
            return iceRespose;
        }*/

      /*  boolean rs = checkIsIgnore(iceRequest.getService(), iceRequest.getMethod());
        if (rs) {
            // 为true表示需要验证token
            iceRespose iceRespose1 = checkToken(iceRequest);
            if (iceRespose1 != null) {
                return iceRespose1;
            }
        }*/

/*        if (httpServletRequest instanceof MultipartHttpServletRequest) {
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) httpServletRequest;
            Map<String, MultipartFile> fileMap = mRequest.getFileMap();
            for (Map.Entry<String, MultipartFile> stringMultipartFileEntry : fileMap.entrySet()) {
                String key = stringMultipartFileEntry.getKey();
                MultipartFile value = stringMultipartFileEntry.getValue();
                String oriName = value.getOriginalFilename();
                InputStream inputStream = value.getInputStream();

                *//*StorePath path = storageClient.uploadFile(inputStream, value.getSize(), Files.getFileExtension(oriName),
                        null);
                String finalPath = fileStorePre + path.getFullPath() + "?attname="
                        + URLEncoder.encode(oriName, "UTF-8");
                iceRequest.addAttr(key, finalPath);*//*

            }
        }*/

        // 请求服务：RPC请求或者http请求。
        IceRespose iceRespose = invoke(iceRequest);
        log.info("Api Server - App call:[{}.{}] to spend:{}ms", iceRequest.getService(), iceRequest.getMethod(),
                (System.currentTimeMillis() - begin));

        return iceRespose;
    }

    private IceRespose invoke(IceRequest iceRequest) {
        String service = iceRequest.getService();
        String method = iceRequest.getMethod();

        IceRespose iceRespose = new IceRespose();
        if (service == null || service.length() == 0) {
            iceRespose.setCode(2, "服务名为空,请求失败!");
            return iceRespose;
        }
        if (method == null || method.length() == 0) {
            iceRespose.setCode(2, "方法名为空,请求失败!");
            return iceRespose;
        }
        try {
            String json = IceClientUtils.doService(iceRequest);
            return JsonUtils.toObject(json, IceRespose.class);
        } catch (Exception e) {
            log.error("API调用:[{}.{}]RPC服务时异常：" + e.toString(), service, method);
            iceRespose.setMsg("RPC 异常");
            e.printStackTrace();

        }
        return iceRespose;
    }

    protected String[] parseServiceAndMethod(String apiName) {
        if (StringUtils.isEmpty(apiName))
            return null;
        int lastInx = StringUtils.lastIndexOf(apiName, ".");
        return new String[]{StringUtils.substring(apiName, 0, lastInx), StringUtils.substring(apiName, lastInx + 1)};
    }

    private void assembleContextParams(HttpServletRequest request, IceRequest context) {
        // 设置扩展参数
        context.setExtra("queryString", request.getQueryString());
        context.setExtra("remoteHost", request.getRemoteHost());
        context.setExtra("requestURI", request.getRequestURI());
        context.setExtra("requestURL", request.getRequestURL().toString());
        context.setExtra("remoteAddr", request.getRemoteAddr());
        context.setExtra("remoteHost", request.getRemoteHost());
        context.setExtra("remotePort", request.getRemotePort() + "");
        context.setExtra("serverName", request.getServerName());
        context.setExtra("serverPort", request.getServerPort() + "");
        context.setExtra("userAgent", request.getHeader("User-Agent"));
        context.setExtra("token", request.getHeader("token"));
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ip)) {
            ip = request.getRemoteAddr();
        }
        context.setExtra("ip", ip);
        // 设置系统参数
        Map<String, String> requestParams = clearReuestParams(request.getParameterMap());

        // 设置业务参数
        if (requestParams != null) {
            String paramJson = requestParams.get("params");
            if (paramJson != null && paramJson.length() > 0) {
                @SuppressWarnings("unchecked")
                Map<String, Object> paramMap = (Map<String, Object>) JsonUtils.toMap(paramJson);
                Set<String> paramKeySet = paramMap.keySet();
                for (String key : paramKeySet) {
                    context.addAttr(key, String.valueOf(paramMap.get(key)));
                }
            }
        }
    }

    /**
     * 清除请求参数中的重名或者不正常的参数
     *
     * @param map
     * @return
     */
    private Map<String, String> clearReuestParams(Map<String, String[]> map) {
        Map<String, String> result = new LinkedHashMap<String, String>();
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            if (value == null) {
                result.put(key.toString(), "");
            } else if (value.getClass().isArray()) {
                if (((Object[]) value).length > 0) {
                    result.put(key.toString(), ((Object[]) value)[0].toString());
                } else {
                    result.put(key.toString(), "");
                }
            } else {
                result.put(key.toString(), value.toString());
            }
        }
        return result;
    }
}