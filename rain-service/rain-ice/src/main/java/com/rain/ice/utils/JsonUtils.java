package com.rain.ice.utils;

import com.alibaba.fastjson.JSON;
import com.rain.ice.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class JsonUtils {

	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

	public static String toJson(Object object) {
		return JSON.toJSONString(object);
	}

 	private JsonUtils(){

	}
	 
	public static Map<?, ?> toMap(String json) {
		try {
			return JSON.parseObject(json, Map.class);
		} catch (Exception e) {
			logger.error("{}",e);
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
