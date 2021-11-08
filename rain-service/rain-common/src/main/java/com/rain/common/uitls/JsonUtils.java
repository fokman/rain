package com.rain.common.uitls;

import cn.hutool.json.JSONUtil;
import com.rain.common.exception.ServiceException;

import java.util.Map;

public class JsonUtils {
    public static String toJson(Object object) {
        return JSONUtil.toJsonStr(object);
    }


    public static Map<?, ?> toMap(String json) {
        try {
            return JSONUtil.toBean(json, Map.class);
        } catch (Exception e) {
            throw new ServiceException("解析失败:" + e.getMessage());
        }
    }


    public static <T> T toObject(String json, Class<T> c) {
        try {
            return JSONUtil.toBean(json, c);
        } catch (Exception e) {
            throw new ServiceException("解析失败:" + e.getMessage());
        }
    }

}
