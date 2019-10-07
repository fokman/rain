package com.rain.common.servcie.ds;

import java.util.Properties;

import com.rain.common.servcie.StartupService;
import com.rain.common.uitls.ContextUtils;
import lombok.extern.slf4j.Slf4j;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.rain.common.servcie.config.Startup;

import javax.sql.DataSource;


@Startup
@Slf4j
public class DataSourceStartUp implements StartupService {

	
	@Override
	public void startup(Properties properties)   {
		try {
//			log.info("dataSource-service-start");
			DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
			ContextUtils.setDataSource(dataSource);
//			log.info("dataSource-service-end");
		} catch (Exception e) {
			log.error("dataSource-service-error",e);
			throw new RuntimeException(e);
		}
	}

	 
}
