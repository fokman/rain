package com.rain.common.uitls;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.UUID;

/**
 * Lang工具类
 *
 */
public class LangUtils {
	/**
	 * 生产UUID
	 * @return UUID
	 */
	public static String randomUUID() {
		return UUID.randomUUID().toString();
	}

	/**
	 * 获取boolean类型的值,是否等于true
	 * @param obj 对象
	 * @return boolean类型的值
	 */
	public static boolean parseBoolean(Object obj) {
		return parseBoolean(obj, false);
	}

	/**
	 * 获取boolean类型的值,是否等于true
	 * @param obj obj 对象
	 * @param defaultValue 默认值
	 * @return boolean类型的值
	 */
	public static boolean parseBoolean(Object obj, boolean defaultValue) {
		if (obj == null) {
			return defaultValue;
		} else if (obj instanceof String) {
			return ((String) obj).equalsIgnoreCase("true");
		} else if (obj instanceof Number) {
			return ((Number) obj).intValue() == 1;
		} else if (obj instanceof Boolean) {
			return ((Boolean) obj).booleanValue();
		}
		return defaultValue;
	}

	/**
	 * 获取short类型的值,默认0
	 * @param obj 对象
	 * @return short类型的值
	 */
	public static short parseShort(Object obj) {
		return parseShort(obj, (short) 0);
	}

	/**
	 * 获取short类型的值
	 * @param obj 对象
	 * @param defaultValue 默认值
	 * @return short类型的值
	 */
	public static short parseShort(Object obj, short defaultValue) {
		if (obj == null) {
			return defaultValue;
		} else if (obj instanceof String) {
			try {
				String str = (String) obj;
				return Short.parseShort(str.trim());
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		} else if (obj instanceof Number) {
			return ((Number) obj).shortValue();
		}
		return defaultValue;
	}

	// ----------------------------------------------------INT------------------------------------------------------------
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

	// ---------------------------------------------------LONG------------------------------------------------------------
	/**
	 * 获取long类型的值,默认0
	 * @param obj 对象
	 * @return long类型的值
	 */
	public static long parseLong(Object obj) {
		return parseLong(obj, 0);
	}

	/**
	 * 获取long类型的值
	 * @param obj 对象
	 * @param defaultValue 默认值
	 * @return long类型的值
	 */
	public static long parseLong(Object obj, long defaultValue) {
		if (obj == null) {
			return defaultValue;
		} else if (obj instanceof String) {
			try {
				return Long.parseLong(((String) obj).trim());
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		} else if (obj instanceof Number) {
			return ((Number) obj).longValue();
		}
		return defaultValue;
	}

	// ---------------------------------------------------FLOAT------------------------------------------------------------
	/**
	 * 获取float类型的值,默认0
	 * @param obj 对象
	 * @return float类型的值
	 */
	public static float parseFloat(Object obj) {
		return parseFloat(obj, 0);
	}

	/**
	 * 获取float类型的值
	 * @param obj 对象
	 * @param defaultValue 默认值
	 * @return float类型的值
	 */
	public static float parseFloat(Object obj, float defaultValue) {
		if (obj == null) {
			return defaultValue;
		} else if (obj instanceof String) {
			try {
				return Float.parseFloat(((String) obj).trim());
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		} else if (obj instanceof Number) {
			return ((Number) obj).floatValue();
		}
		return defaultValue;
	}

	/**
	 * 获取double类型的值,默认0
	 * @param str 对象
	 * @return double类型的值
	 */
	public static double parseDouble(Object str) {
		return parseDouble(str, 0);
	}

	/**
	 * 获取double类型的值
	 * @param obj 对象
	 * @param defaultValue 默认值
	 * @return double类型的值
	 */
	public static double parseDouble(Object obj, double defaultValue) {
		if (obj == null) {
			return defaultValue;
		} else if (obj instanceof String) {
			try {
				return Double.parseDouble(((String) obj).trim());
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		} else if (obj instanceof Number) {
			return ((Number) obj).doubleValue();
		}
		return defaultValue;
	}

	/**
	 * 获取BigDecimal类型的值,默认0
	 * @param obj 对象
	 * @return BigDecimal类型的值
	 */
	public static BigDecimal parseBigDecimal(Object obj) {
		return parseBigDecimal(obj, new BigDecimal(0));
	}

	/**
	 * 获取BigDecimal类型的值
	 * @param obj 对象
	 * @param defaultValue 默认值
	 * @return BigDecimal类型的值
	 */
	public static BigDecimal parseBigDecimal(Object obj, BigDecimal defaultValue) {
		if (obj == null) {
			return defaultValue;
		} else if (obj instanceof String) {
			try {
				String s = (String) obj;
				s = s.trim();
				if (s.length() == 0) {
					s = "0";
				}
				return new BigDecimal(s);
			} catch (NumberFormatException e) {
				return defaultValue;
			}
		} else if (obj instanceof BigDecimal) {
			return (BigDecimal) obj;
		}
		return defaultValue;
	}

	// ------------------------------------------------BigDecimal--------------------------------------------------------------
	public static String formatNumber(Object obj) {
		return formatNumber(obj, "#,##0.00");
	}

	public static String formatNumberEmpty(Object obj) {
		if (obj == null) {
			obj = new BigDecimal(0);
		}
		if (((Number) obj).intValue() == 0) {
			return "";
		}
		return formatNumber(obj);
	}

	public static String formatNumber(Object obj, String pattern) {
		if (obj == null) {
			obj = new BigDecimal(0);
		}
		NumberFormat nf = new DecimalFormat(pattern);
		return nf.format(obj);
	}
}
