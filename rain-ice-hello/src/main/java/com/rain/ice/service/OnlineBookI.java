package com.rain.ice.service;

import Ice.Current;
import com.rain.ice.book.Message;
import com.rain.ice.book._OnlineBookDisp;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/10
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class OnlineBookI extends _OnlineBookDisp {

    @Override
    public Message bookTick(Message msg, Current __current) {
        System.out.println(msg);
        return msg;
    }
}
