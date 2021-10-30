package com.rain.common.uitls;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @author kunliu
 */
@Slf4j
public class AppUtils {

	public static String getHome() {
		String home = System.getenv("RAIN_HOME");
		if (!StringUtils.isEmpty(home)) {
			home = home.replaceAll("\\\\", "/");
		}else{
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

	public static InputStream getEnvResource(String config){
		return getResource(getHome()+"/env/"+config);
	}

	public static InputStream getResource(String config) {
		try {
			return new BufferedInputStream(new FileInputStream(config));
		} catch (FileNotFoundException e) {
			return null;
		}	
	}
	    

}
