package com.rain.ice.utils;

import com.rain.ice.service.IceServiceRegister;
import com.rain.ice.service.config.Service;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class InitRegisterService {

    private static final String PKGS = "com.rain";

    public static void init() {
        try {
            List<String> pkgList = new ArrayList<>();
            String[] strs = PKGS.split(";");
            for (String string : strs) {
                if (StringUtils.isNotEmpty(string)) {
                    pkgList.add(string.trim());
                }
            }

            // 加载
            for (String string : pkgList) {
                Set<Class<?>> list = ClassUtils.getClasses(string);
                for (Class<?> clazz : list) {
                    Service[] services = clazz.getDeclaredAnnotationsByType(Service.class);
                    for (Service service : services) {
                        String key = service.name();
                        log.info("register service name:{}, class: {}", key, clazz.getName());
                        IceServiceRegister.getInstance().putIceService(key, clazz.newInstance());
                    }
                }
            }
            log.info("startup-init-end");
        } catch (Exception e) {
            log.error("start-init-error：{}", e);
        }
    }

}
