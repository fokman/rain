package com.rain.common.uitls;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AppUtils {

/*	private static SqlSessionFactory sqlSession = null;

	public static void setSqlSession(SqlSessionFactory sqlSession) {
		AppUtils.sqlSession = sqlSession;
	}

	public static SqlSessionFactory getSqlSession() {
		return sqlSession;
	}*/

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
