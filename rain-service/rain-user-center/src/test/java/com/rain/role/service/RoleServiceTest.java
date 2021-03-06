package com.rain.role.service;

import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceRespose;
import com.rain.common.uitls.IdWorker;
import com.rain.common.uitls.JsonUtils;
import com.rain.common.uitls.TestClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/19
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
@RunWith(JUnit4.class)
@Slf4j
public class RoleServiceTest {

    @Test
    public void testAdd() {
        IceRequest iceRequest = new IceRequest();
        iceRequest.setMethod("add");
        iceRequest.setService("RoleService");
        iceRequest.setAttr("id", String.valueOf(IdWorker.getFlowIdWorkerInstance().nextId()));
        iceRequest.setAttr("code", "User");
        iceRequest.setAttr("name", "管理员");
        iceRequest.addAttr("updateTime", new Date());
        iceRequest.setAttr("tenantId", "1101");
        log.info("request params:{}", JsonUtils.toJson(iceRequest));
        IceRespose iceResponse = TestClientUtils.doService(iceRequest, new String[]{});
        log.info("message {}", JsonUtils.toJson(iceResponse));
    }

    @Test
    public void testDelete() {
        IceRequest iceRequest = new IceRequest();
        iceRequest.setMethod("delete");
        iceRequest.setService("RoleService");
        iceRequest.setAttr("id", "699795998179856384");
        log.info("request params:{}", JsonUtils.toJson(iceRequest));
        IceRespose iceResponse = TestClientUtils.doService(iceRequest, new String[]{});
        log.info("message {}", JsonUtils.toJson(iceResponse));
    }

    @Test
    public void update(){
        IceRequest iceRequest = new IceRequest();
        iceRequest.setMethod("update");
        iceRequest.setService("RoleService");
        iceRequest.setAttr("id", "700117304242868224");
        iceRequest.setAttr("code", "Admin");
        iceRequest.setAttr("name", "系统管理员");
        iceRequest.addAttr("updateTime", new Date());
        iceRequest.setAttr("tenantId", "1102");
        log.info("request params:{}", JsonUtils.toJson(iceRequest));
        IceRespose iceResponse = TestClientUtils.doService(iceRequest, new String[]{});
        log.info("message {}", JsonUtils.toJson(iceResponse));
    }

}
