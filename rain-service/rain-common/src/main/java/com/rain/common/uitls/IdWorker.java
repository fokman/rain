package com.rain.common.uitls;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdWorker {

    private static Snowflake getInstance() {
        Long dataCenterId = 1L;
        Long workerId = 0L;
        return IdUtil.getSnowflake(workerId, dataCenterId);
    }

    public static Long getId() {
        return getInstance().nextId();
    }

    public static String getIdStr() {
        return getInstance().nextIdStr();
    }


}
