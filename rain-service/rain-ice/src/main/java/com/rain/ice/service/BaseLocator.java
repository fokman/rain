package com.rain.ice.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/25
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class BaseLocator implements BeanFactoryAware {
    private static BeanFactory beanFactory;
    private static BaseLocator baselocator = null;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public static BaseLocator getInstance() {
        if (baselocator == null)
            baselocator = (BaseLocator) beanFactory.getBean("baseLocator");
        return baselocator;
    }

}
