package com.rain.common.uitls;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@Slf4j
public class AppUtils {

    private AppUtils() {

    }

    public static AppUtils getInstance() {
        return AppUtilsHolder.singleton;
    }

    private static class AppUtilsHolder {
        private static final AppUtils singleton = new AppUtils();
    }

    /**
     * 读取环境变量
     *
     * @return
     */
    public String getEnvHome() {
        String home = System.getenv("RAIN_HOME");
        if (!StringUtils.isEmpty(home)) {
            home = home.replaceAll("\\\\", "/");
        } else {
            String sysName = StringUtils.trimNull(System.getProperty("os.name"));
            sysName = sysName.toLowerCase();
            if (sysName.contains("windows")) {
                home = "C:/rain-server";
            } else {
                home = "/Users/kunliu";
            }
        }
        return home;
    }

    public InputStream getEnvResource(String config) {
        return getResource(getEnvHome() + "/env/" + config);
    }

    public InputStream getResource(String config) {
        try {
            return new BufferedInputStream(new FileInputStream(config));
        } catch (FileNotFoundException e) {
            log.error("{}", e);
            return null;
        }
    }

}
