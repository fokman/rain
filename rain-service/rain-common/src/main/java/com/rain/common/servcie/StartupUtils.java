package com.rain.common.servcie;

import com.rain.common.servcie.config.Startup;
import com.rain.common.uitls.EnvUtils;
import com.rain.common.uitls.ClassUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
public class StartupUtils {

    public static void init() {
        // 加载配置
        Properties properties = getProperties();
        // 启动业务
        try {
            List<String> pkgList = new ArrayList<>();
            String pkgs = (String) properties.get("scan");
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
                startupService.startup(properties);
            }

        } catch (Exception e) {
            log.error("start-init-error", e);
        }
    }

    private static Properties getProperties() {
        Properties prop = new Properties();
        InputStream in = null;
        String config = "/config/app.properties";
        try {
            in = EnvUtils.getEnvResource(config);
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
