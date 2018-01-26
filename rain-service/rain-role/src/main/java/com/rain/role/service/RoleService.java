package com.rain.role.service;

import com.rain.ice.model.IceRequest;
import com.rain.ice.service.config.Service;
import com.rain.ice.utils.GenerateId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/19
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
@Service(name = "RoleService")
public class RoleService{

    private Logger logger = LoggerFactory.getLogger(RoleService.class);

    public void add(IceRequest iceRequest) {
        logger.info("start role service add method.");
        iceRequest.setMethod("add");
        iceRequest.setService("RoleService");
        iceRequest.setAttr("roleId", String.valueOf(GenerateId.getInstance().getId()));
//        roleMapper.insert(iceRequest.getAttr());

    }

}
