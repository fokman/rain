package com.rain.common.servcie.ds;

import com.rain.common.servcie.StartupService;
import com.rain.common.servcie.config.Startup;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


@Startup
@Slf4j
public class DataSourceStartUp implements StartupService {


    @Override
    public void startup(Properties properties) {
        try {
            log.info("dataSource-service-start");
            //DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
            //ContextUtils.setDataSource(dataSource);
            log.info("dataSource-service-end");
        } catch (Exception e) {
            log.error("dataSource-service-error", e);
            throw new RuntimeException(e);
        }
    }


}
