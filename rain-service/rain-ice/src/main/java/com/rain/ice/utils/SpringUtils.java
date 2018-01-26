package com.rain.ice.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/25
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
@Component
public class SpringUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static <T> T getBean(String name) {
        if (applicationContext.containsBean(name)) {
            return (T) applicationContext.getBean(name);
        } else {
            return null;
        }
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> baseType) {

        return applicationContext.getBeansOfType(baseType);
    }

    public static <T> T  getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
