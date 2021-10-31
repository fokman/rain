package com.rain.common.uitls;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.rain.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IdWorker {
    private static Long workerId = 0L;
    private static Long dataCenterId = 1L;

    private static Snowflake getInstance() {
        return IdUtil.getSnowflake(workerId, dataCenterId);
    }

    public static Long getId() {
        return getInstance().nextId();
    }

    public static String getIdStr() {
        return getInstance().nextIdStr();
    }


}
