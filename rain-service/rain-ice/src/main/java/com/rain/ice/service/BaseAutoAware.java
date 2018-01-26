package com.rain.ice.service;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/26
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class BaseAutoAware {
    public BaseAutoAware() {
        ((AutowireCapableBeanFactory) retrieveBeanFactory())
                .autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
    }

    private BeanFactory retrieveBeanFactory() {
        BaseLocator baseLocator = new BaseLocator();
        return baseLocator.getBeanFactory();
    }
}
