package com.rain.common.uitls;

import com.alibaba.fastjson.JSON;
import com.rain.common.exception.ServiceException;

import java.util.List;
import java.util.Map;

public class JsonUtils {
	public static String toJson(Object object) {
		return JSON.toJSONString(object);
	}

 
	 
	public static Map<?, ?> toMap(String json) {
		try {
			return JSON.parseObject(json, Map.class);
		} catch (Exception e) {
			throw new ServiceException("解析失败:" + e.getMessage());
		}
	}

	public static List<?> toList(String json) {
		try {
			return JSON.parseObject(json, List.class);
		} catch (Exception e) {
			throw new ServiceException("解析失败:" + e.getMessage());
		}
	}

	public static <T> T toObject(String json, Class<T> c) {
		try {
			return JSON.parseObject(json, c);
		} catch (Exception e) {
			throw new ServiceException("解析失败:" + e.getMessage());
		}
	}

}
