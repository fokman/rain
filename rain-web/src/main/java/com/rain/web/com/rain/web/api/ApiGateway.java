package com.rain.web.com.rain.web.api;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.google.common.io.Files;
import com.rain.common.ice.model.IceRequest;
import com.rain.common.ice.model.IceRespose;
import com.rain.common.ice.utils.IceClientUtils;
import com.rain.common.uitls.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;


@Configuration
@RestController
@Slf4j
@Import({FdfsClientConfig.class})
//用来处理jmx重复处理bean的问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class ApiGateway {

    @RequestMapping("/api")
    public IceRespose apiExecute(@RequestBody IceRequest iceRequest) throws IOException {
        long begin = System.currentTimeMillis();
        // 请求服务：RPC请求或者http请求。
        IceRespose iceRespose = invoke(iceRequest);
        log.info("Api Server - App call:[{}.{}] to spend:{}ms", iceRequest.getService(), iceRequest.getMethod(),
                (System.currentTimeMillis() - begin));

        return iceRespose;
    }


    @Autowired
    protected FastFileStorageClient fastFileStorageClient;

    @PostMapping(value = "/upload")
    public IceRespose upload(@RequestParam(name = "files") MultipartFile[] multipartFiles, IceRequest iceRequest) {
        IceRespose iceRespose = new IceRespose();
        try {
            if (multipartFiles.length > 0) {
                Map multis = new HashMap();
                for (MultipartFile multipartFile : multipartFiles) {
                    try {
                        String originalFilename = multipartFile.getOriginalFilename();
                        InputStream inputStream = multipartFile.getInputStream();
                        StorePath storePath = fastFileStorageClient.uploadFile(inputStream, multipartFile.getSize(), Files.getFileExtension(originalFilename), null);
                        String finalPath = storePath.getFullPath() + "?attname=" + URLEncoder.encode(originalFilename, "UTF-8");
                        multis.put(originalFilename, finalPath);
                        iceRespose.setData(multis);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            iceRespose.setCode(9001);
            iceRespose.setMsg(e.getMessage());
        }
        return iceRespose;
    }


    /**
     * 装配 context 的参数
     *
     * @param request
     * @param context
     */
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

    /**
     * 启动调用RPC服务
     *
     * @param iceRequest
     * @return
     */
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
            IceRespose json = IceClientUtils.doService(iceRequest);
            return json;
        } catch (Exception e) {
            log.error("API调用:[{}.{}]RPC服务时异常：{}", e.toString(), service, method);
        }
        return iceRespose;
    }

}
