package com.rain.service.role;

import com.rain.ice.model.IceRequest;
import com.rain.ice.model.IceResponse;
import com.rain.ice.utils.GenerateId;
import com.rain.ice.utils.JsonUtils;
import com.rain.ice.utils.TestClientUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.util.Date;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/19
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class RoleServiceTest {

    private Logger logger = LogManager.getLogger(RoleServiceTest.class);

    @Test
    public void testAdd() {
        IceRequest iceRequest = new IceRequest();
        iceRequest.setMethod("add");
        iceRequest.setService("RoleService");
        iceRequest.setAttr("roleId", String.valueOf(GenerateId.getInstance().getId()));
        iceRequest.setAttr("roleName", "系统管理员");
        iceRequest.addAttr("createTime", new Date());
        iceRequest.addAttr("updateTime",new Date());
        IceResponse iceResponse = TestClientUtils.doService(iceRequest);
        System.out.println(JsonUtils.toJson(iceResponse));
//        logger.info("message {}", JsonUtils.toJson(iceResponse));
    }

}