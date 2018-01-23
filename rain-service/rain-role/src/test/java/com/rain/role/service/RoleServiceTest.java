package com.rain.role.service;

import com.rain.ice.model.IceRequest;
import com.rain.ice.model.IceResponse;
import com.rain.ice.utils.DateUtils;
import com.rain.ice.utils.GenerateId;
import com.rain.ice.utils.TestClientUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/19
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
@RunWith(SpringRunner.class)
public class RoleServiceTest {

    @Test
    public void testAdd() {
        IceRequest iceRequest = new IceRequest();
        iceRequest.setMethod("add");
        iceRequest.setService("RoleService");
        iceRequest.setAttr("roleId", String.valueOf(GenerateId.getInstance().getId()));
        iceRequest.setAttr("roleName", "系统管理员");
        iceRequest.setAttr("createTime", DateUtils.formatDateTime(new Date()));

        IceResponse iceResponse = TestClientUtils.doService(iceRequest, new String[]{});
        System.out.println("message:" + iceResponse.getData());
    }
}
