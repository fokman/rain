package com.rain.common.utils;

import com.rain.common.uitls.RedisUtils;

public class RedisUtilsTest {
    public static void main(String[] args) throws Exception {
        RedisUtils.setex("session_id", 5, "zhangjing");
        Thread.sleep(3);
        RedisUtils.expire("session_id", 5);
        long start = System.currentTimeMillis();
        while (true) {
            String key = RedisUtils.get("session_id");
            if (key == null) {
                break;
            }
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
