package com.rain.common.servcie;

import com.rain.common.servcie.config.Startup;
import com.rain.common.uitls.AppUtils;
import com.rain.common.uitls.ClassUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
public class StartupUtils {

    public static void init() {
        log.info("start-init-begin");
        // 加载配置
        Properties context = getProperties();
        // 启动业务
        try {
            List<String> pkgList = new ArrayList<>();
            String pkgs = (String) context.get("scan");
            if (pkgs != null) {
                pkgs = pkgs.trim();
                String[] strs = pkgs.split(";");
                for (String string : strs) {
                    if (string != null && string.trim().length() > 0) {
                        pkgList.add(string.trim());
                    }
                }
            }

            // 加载
            List<SetupClass> startups = new ArrayList<>();
            for (String string : pkgList) {
                Set<Class<?>> list = ClassUtils.getClasses(string);
                for (Class<?> class1 : list) {
                    Startup[] startupAnns = class1.getDeclaredAnnotationsByType(Startup.class);
                    if (startupAnns.length > 0) {
                        SetupClass setupClass = new SetupClass();
                        setupClass.setSort(startupAnns[0].sort());
                        setupClass.setClas(class1);
                        startups.add(setupClass);
                    }
                }
            }
            Collections.sort(startups);
            // 加载启动
            for (SetupClass class1 : startups) {
                StartupService startupService = (StartupService) class1.getClas().newInstance();
                startupService.startup(context);
            }

            log.info("startup-init-end");
        } catch (Exception e) {
            log.error("start-init-error", e);
        }
    }

    private static Properties getProperties() {
        log.info("start-init-load-config-begin");
        Properties prop = new Properties();
        InputStream in = null;
        String config = "/config/app.properties";
        try {
            in = AppUtils.getEnvResource(config);
            prop.load(in);

            log.info("start-init-load-config-end");
        } catch (IOException e) {
            log.error("start-init-load-config-error", e);
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return prop;
    }

}
