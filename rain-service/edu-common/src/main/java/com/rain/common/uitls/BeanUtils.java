package com.rain.common.uitls;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanUtils {
	/**
	 * 创建Bean实例
	 * @param c Bean的Class
	 * @return Bean实例
	 * @throws Exception 异常
	 */
	public static <T> T newInstance(Class<T> c) {
		try {
			return c.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 获取字段的值
	 * @param obj 对象
	 * @param field 字段
	 * @return 字段的值
	 */
	public static Object getFieldValue(Object obj, Field field) {
		Method method = getReadMethod(obj.getClass(), field);
		return invokeMethod(obj, method);
	}

	/**
	 * 调用获取方法
	 * @param obj 对象
	 * @param method 方法
	 * @return 方法的值
	 */
	public static Object getMethodValue(Object obj, Method method) {
		return invokeMethod(obj, method);
	}

	/**
	 * 设置字段的值
	 * @param obj 对象
	 * @param field 字段
	 * @param value 字段的值
	 */
	public static void setFieldValue(Object obj, Field field, Object value) {
		Method method = getWriterMethod(obj.getClass(), field);
		invokeMethod(obj, method);
	}

	/**
	 * 调用设置方法
	 * @param obj 对象
	 * @param method 方法
	 * @param value 方法的值
	 */
	public static void setMethodValue(Object obj, Method method, Object value) {
		invokeMethod(obj, method, value);
	}

	/**
	 * 获取字段的读取方法
	 * @param clas 类
	 * @param field 字段
	 * @return 字段的读取方法
	 */
	public static Method getReadMethod(Class<?> clas, Field field) {
		Class<?> type = field.getType();
		String methodName = "get";
		if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(boolean.class)) {
			methodName = "is";
		}
		methodName += field.getName().substring(0, 1).toUpperCase();
		methodName += field.getName().substring(1);
		return getMethod(clas, methodName);
	}

	/**
	 * 获取字段的设置方法
	 * @param clas 类
	 * @param field 字段
	 * @return 字段的设置方法
	 */
	public static Method getWriterMethod(Class<?> clas, Field field) {
		String methodName = "set";
		methodName += field.getName().substring(0, 1).toUpperCase();
		methodName += field.getName().substring(1);
		return getMethod(clas, methodName, field.getType());
	}

	/**
	 * 获取一个方法
	 * @param clas 类
	 * @param methodName 方法名
	 * @param parameterTypes 参数类型
	 * @return 方法
	 */
	public static Method getMethod(Class<?> clas, String methodName, Class<?>... parameterTypes) {
		try {
			return clas.getMethod(methodName, parameterTypes);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 动态调用一个方法
	 * @param obj 对象
	 * @param method 方法
	 * @param args 参数
	 * @return 方法返回值
	 */
	public static Object invokeMethod(Object obj, Method method, Object... args) {
		try {
			return method.invoke(obj, args);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}