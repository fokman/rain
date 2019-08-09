package com.rain.common.uitls;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;

public class AppUtils {

	private static SqlSessionFactory sqlSession = null;

	public static void setSqlSession(SqlSessionFactory sqlSession) {
		AppUtils.sqlSession = sqlSession;
	}

	public static SqlSessionFactory getSqlSession() {
		return sqlSession;
	}

	public static String getEduHome() {
		String home = System.getenv("EDU_HOME");
		if (StringUtils.isNotEmpty(home)) {
			home = home.replaceAll("\\\\", "/");
		}
		if (StringUtils.isEmpty(home)) {
			String sysName = System.getProperty("os.name").trim();
			sysName = sysName.toLowerCase();
			if (sysName.contains("windows")) {
				home = "D:/edu-server";
			} else {
				home = "/usr/local/edu-server";
			}
		}
		return home;
	}
	public static String getEduHomeEnv() {
		String home = getEduHome()+"/env/";
		return home;
	}	
	public static InputStream getEnvResource(String config){
		return getResource(getEduHome()+"/env/"+config);
	}
	public static InputStream getResource(String config) {
		try {
			return new BufferedInputStream(new FileInputStream(config));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	    
	
	public static void main(String[] args) {
		System.out.println(getEduHome());
	}
}
