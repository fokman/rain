package com.rain.role.service;

import com.rain.common.ice.model.IceRequest;
import com.rain.common.ice.model.IceRespose;
import com.rain.common.servcie.BaseService;
import com.rain.common.servcie.config.Service;
import com.rain.common.uitls.IdWorker;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class RoleService extends BaseService {

    private String ROLE_MAPPER = "RoleMapper";

    private String INSERT = "insert";

    public IceRespose add(IceRequest iceRequest) {
        log.info("start role service add method.");
        iceRequest.setMethod("add");
        iceRequest.setService("RoleService");
        iceRequest.setAttr("id", String.valueOf(IdWorker.getFlowIdWorkerInstance().nextId()));
        iceRequest.setAttr("code", "Admin");
        iceRequest.setAttr("name", "管理员");
        iceRequest.addAttr("createTime", new Date());
        iceRequest.addAttr("createTime", new Date());
        iceRequest.setAttr("tenantId", "1101");
        getDao().insert(ROLE_MAPPER, INSERT, iceRequest.getAttrMap());
        IceRespose response = new IceRespose();
        response.setCode(200, "insert role success!");
        return response;
    }

}
