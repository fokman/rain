package com.rain.common.uitls;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EnvUtils {

    public static String getEduHome() {
        String home = System.getenv("EDU_HOME");
        if (StringUtils.isNotEmpty(home)) {
            home = home.replaceAll("\\\\", "/");
        }
        if (StringUtils.isEmpty(home)) {
            String sysName = System.getProperty("os.name").trim();
            sysName = sysName.toLowerCase();
            if (sysName.contains("windows")) {
                home = "D:/edu-server/env/";
            } else {
                home = "/usr/local/edu-server";
            }
        }
        return home;
    }


    public static InputStream getEnvResource(String config) {
        return getResource(getEduHome() + config);
    }

    public static InputStream getResource(String config) {
        try {
            return new BufferedInputStream(new FileInputStream(config));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}
