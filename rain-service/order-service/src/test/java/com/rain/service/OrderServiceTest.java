package com.rain.service;

import com.rain.ice.mode.IceRequest;
import com.rain.ice.mode.IceResponse;
import com.rain.ice.utils.TestClientUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/15
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @Test
    public void add(){
        IceRequest iceRequest = new IceRequest();
        iceRequest.setService("com.rain.service.OrderService");
        iceRequest.setMethod("add");
        IceResponse iceResponse = TestClientUtils.doService(iceRequest, new String[] {});
        System.out.println("message:"+iceResponse.getData());
    }
}
