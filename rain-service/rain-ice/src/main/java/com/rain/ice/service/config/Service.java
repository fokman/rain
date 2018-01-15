package com.rain.ice.service.config;

import java.lang.annotation.*;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/11
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Service {
    String name();
}
