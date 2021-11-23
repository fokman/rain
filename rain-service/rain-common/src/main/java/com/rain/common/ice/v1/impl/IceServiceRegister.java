package com.rain.common.ice.v1.impl;

import lombok.NoArgsConstructor;

import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor
public class IceServiceRegister {

    private static final ConcurrentHashMap<String, Object> registerMap = new ConcurrentHashMap<>();
    private static final IceServiceRegister register = new IceServiceRegister();

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
