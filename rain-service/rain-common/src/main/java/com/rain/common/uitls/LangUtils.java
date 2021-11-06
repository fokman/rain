package com.rain.common.uitls;

/**
 * Lang工具类
 *
 * @author kunliu
 */
public class LangUtils {

	/**
	 * 获取int类型的值,默认0
	 * @param obj 对象
	 * @return int类型的值
	 */
	public static int parseInt(Object obj) {
		return parseInt(obj, 0);
	}

	/**
	 * 获取int类型的值
	 * @param obj 对象
	 * @param defaultValue 默认值
	 * @return int类型的值
	 */
	public static int parseInt(Object obj, int defaultValue) {
		if (obj == null) {
			return defaultValue;
		} else if (obj instanceof String) {
			try {
				return Integer.parseInt(((String) obj).trim());
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		} else if (obj instanceof Number) {
			return ((Number) obj).intValue();
		}
		return defaultValue;
	}

}
