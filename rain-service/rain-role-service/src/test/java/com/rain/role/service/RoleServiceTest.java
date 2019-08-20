package com.rain.role.service;

import com.rain.common.ice.model.IceRequest;
import com.rain.common.ice.model.IceRespose;
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
        iceRequest.setAttr("roleId", String.valueOf(IdWorker.getFlowIdWorkerInstance().nextId()));
        iceRequest.setAttr("roleName", "系统管理员");
        iceRequest.addAttr("createTime", new Date());
        iceRequest.addAttr("updateTime", new Date());
        IceRespose iceResponse = TestClientUtils.doService(iceRequest, new String[]{});
        log.info("message {}", JsonUtils.toJson(iceResponse));
    }

}
