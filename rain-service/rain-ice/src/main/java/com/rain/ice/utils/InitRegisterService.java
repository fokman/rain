package com.rain.ice.utils;

import com.rain.ice.service.IceServiceRegister;
import com.rain.ice.service.config.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class InitRegisterService {
    private static final Logger logger = LoggerFactory.getLogger(InitRegisterService.class);


    public static void init() {
        logger.info("start up utils init");
        try {
            List<String> pkgList = new ArrayList<>();
            String packages = "com.rain";
            if (packages != null) {
                packages = packages.trim();
                String[] strs = packages.split(";");
                for (String string : strs) {
                    if (string != null && string.trim().length() > 0) {
                        pkgList.add(string.trim());
                    }
                }
            }

            // 加载
            for (String string : pkgList) {
                Set<Class<?>> list = ClassUtils.getClasses(string);
                for (Class<?> clazz : list) {
                    Service[] services = clazz.getDeclaredAnnotationsByType(Service.class);
                    for (Service service : services) {
                        String key = service.name();
                        logger.info("register service name:{}, class: {}", key, clazz.getName());
                        IceServiceRegister.getInstance().putIceService(key, clazz.newInstance());
                    }
                }
            }

            logger.info("startup-init-end");
        } catch (Exception e) {
            logger.error("start-init-error", e);
        }
    }

}
