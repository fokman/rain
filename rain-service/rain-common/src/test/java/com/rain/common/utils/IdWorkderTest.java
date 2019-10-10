package com.rain.common.utils;

import com.rain.common.uitls.IdWorker;

public class IdWorkderTest {
    public static void main(String[] args) throws Exception {
        System.out.println(System.currentTimeMillis());
        IdWorker idWorker = IdWorker.getFlowIdWorkerInstance();
        System.out.println(idWorker.nextId());
        System.out.println(idWorker.nextId());
    }
}
