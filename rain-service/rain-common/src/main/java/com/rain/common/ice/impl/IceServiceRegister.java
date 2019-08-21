package com.rain.common.ice.impl;

import java.util.concurrent.ConcurrentHashMap;

public class IceServiceRegister {

    private static final ConcurrentHashMap<String, Object> registerMap = new ConcurrentHashMap<>();

    private IceServiceRegister() {
    }

    private static class IceServiceHolder {
        private static IceServiceRegister register = new IceServiceRegister();

    }

    public Object getService(String key) {
        return registerMap.get(key);
    }

    public void putIceService(String key, Object iceService) {
        registerMap.put(key, iceService);
    }

    public static IceServiceRegister getInstance() {
        return IceServiceHolder.register;
    }

}
