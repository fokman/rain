package com.rain.service;

import com.rain.ice.model.IceRequest;
import com.rain.ice.model.IceResponse;
import com.rain.ice.service.config.Service;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/15
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
@Service(name = "OrderService")
public class OrderService {

    public IceResponse add(IceRequest iceRequest) {
        IceResponse iceResponse = new IceResponse();
        iceResponse.setCode(200);
        return iceResponse;
    }
}
