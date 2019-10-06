package com.rain.common.servcie.ds;

import java.util.Properties;

import com.rain.common.servcie.StartupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.rain.common.servcie.config.Startup;


@Startup
public class DataSourceStartUp implements StartupService {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceStartUp.class);
	
	@Override
	public void startup(Properties properties)   {
		try {
			logger.info("dataSource-service-start");
			//DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
			//ContextUtils.setDataSource(dataSource);
			logger.info("dataSource-service-end");
		} catch (Exception e) {
			logger.error("dataSource-service-error",e);
			throw new RuntimeException(e);
		}
	}

	 
}
