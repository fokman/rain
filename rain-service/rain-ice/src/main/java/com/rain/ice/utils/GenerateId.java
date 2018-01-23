package com.rain.ice.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/19
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class GenerateId {
    private Logger logger = LoggerFactory.getLogger(GenerateId.class);

    private static final GenerateId instance = new GenerateId();

    private GenerateId() {

    }

    public static GenerateId getInstance() {
        return GenerateId.instance;
    }

    public Long getId() {
        IdWorker idWorker = new IdWorker(1);
        try {
            return idWorker.nextId();
        } catch (Exception e) {
            logger.error("{}", e);
        }
        return 0L;
    }

}
