package com.rain.common.ice.impl;

import java.util.concurrent.ConcurrentHashMap;

public class IceServiceRegister {

    private static final ConcurrentHashMap<String, Object> registerMap = new ConcurrentHashMap<String, Object>();
    private static IceServiceRegister register = new IceServiceRegister();

    private IceServiceRegister() {
    }

    public Object getService(String key) {
        return registerMap.get(key);
    }

    public void putIceService(String key, Object iceService) {
        registerMap.put(key, iceService);
    }

    public static IceServiceRegister getInstance() {
        return register;
    }

}
