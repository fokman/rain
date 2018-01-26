package com.rain.ice.service.config;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Project Name: yudo-biz
 * <p>
 * Create Date: 2017/12/22
 * <p>
 * Copyright(c) 2017 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
@Configuration
@EnableTransactionManagement
public class DruidDataSourceConfig implements EnvironmentAware {


    private RelaxedPropertyResolver propertyResolver;
    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment,"srping.datasource.");
    }

}
