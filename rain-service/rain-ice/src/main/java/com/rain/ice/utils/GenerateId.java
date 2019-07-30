package com.rain.ice.utils;

import com.zeroc.Ice.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/19
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
@Slf4j
public class GenerateId {

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
            log.error("{}", e);
        }
        return 0L;
    }

}
