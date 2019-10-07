package com.rain.role.service;

import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceRespose;
import com.rain.common.servcie.BaseService;
import com.rain.common.servcie.config.Service;
import com.rain.common.servcie.config.Transactional;
import com.rain.common.uitls.IdWorker;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/19
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
@Service(name = "RoleService")
@Slf4j
public class RoleService extends BaseService {

    private String ROLE_MAPPER = "RoleMapper";

    private String INSERT = "insert";

    @Transactional
    public IceRespose add(IceRequest iceRequest) {
        log.info("start role service add method.");
        iceRequest.setAttr("id", String.valueOf(IdWorker.getFlowIdWorkerInstance().nextId()));
        iceRequest.setAttr("name","系统管理员");
        iceRequest.setAttr("code","ADMIN");
        iceRequest.addAttr("createTime", new Date());
        iceRequest.addAttr("updateTime", new Date());
        iceRequest.setAttr("tenantId", "1101");
        getDao().insert(ROLE_MAPPER, INSERT, iceRequest.getAttrMap());
        IceRespose response = new IceRespose();
        response.setCode(200, "insert role success!");
        return response;
    }

}
