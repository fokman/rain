package com.rain.ice.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AppUtils {
    

    /*public static String getEduHome() {
        String home = System.getenv("EDU_HOME");
        if (!StringUtils.isEmpty(home)) {
            home = home.replaceAll("\\\\", "/");
        }
        if (StringUtils.isEmpty(home)) {
            String sysName = StringUtils.trimNull(System.getProperty("os.name"));
            sysName = sysName.toLowerCase();
            if (sysName.contains("windows")) {
                home = "D:/edu-server";
            } else {
                home = "/usr/local/edu-server";
            }
        }
        return home;
    }
    */
/*	public static String getEduHomeEnv() {
        String home = getEduHome()+"/env/";
		return home;
	}	
	*/

    public static InputStream getEnvResource(String config) {
        return getResource(config);
    }

    public static InputStream getResource(String config) {
        try {
            return new BufferedInputStream(new FileInputStream(config));
        } catch (FileNotFoundException e) {
            return null;
        }
    }

}
