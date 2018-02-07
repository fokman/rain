package com.rain.role.service;

import com.rain.ice.model.IceRequest;
import com.rain.ice.model.IceResponse;
import com.rain.ice.service.BaseService;
import com.rain.ice.service.config.Service;
import com.rain.ice.utils.GenerateId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/19
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
@Service(name = "RoleService")
public class RoleService extends BaseService {

    private Logger logger = LoggerFactory.getLogger(RoleService.class);

    private String ROLE_MAPPER = "RoleMapper";

    private String INSERT = "insert";

    public IceResponse add(IceRequest iceRequest) {
        logger.info("start role service add method.");
        iceRequest.setMethod("add");
        iceRequest.setService("RoleService");
        iceRequest.setAttr("roleId", String.valueOf(GenerateId.getInstance().getId()));
        iceRequest.addAttr("createTime", new Date());
        getDao().insert(ROLE_MAPPER, INSERT, iceRequest.getAttrMap());
        IceResponse response = new IceResponse();
        response.setCode(200, "insert role success!");
        return response;
    }

}
