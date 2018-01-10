package com.rain.ice.service;

import Ice.Communicator;
import Ice.Object;
import IceBox.Service;

import java.util.Arrays;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/10
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class BoxOnlineBookI implements Service {
    private Ice.ObjectAdapter objectAdapter;

    @Override
    public void start(String name, Communicator communicator, String[] strings) {
        objectAdapter = communicator.createObjectAdapter(name);
        Object object = new OnlineBookI();
        objectAdapter.add(object,communicator.stringToIdentity(name));
        objectAdapter.activate();
        System.out.println("name="+name+",service started,with param size="+strings.length+",detail:"+ Arrays.toString(strings));
    }

    @Override
    public void stop() {
        objectAdapter.destroy();
    }
}
