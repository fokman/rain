package com.rain.common.uitls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 字符串工具类
 */
public class StringUtils {
    /**
     * 判断字符串是否为数组串
     *
     * @param str 字符串
     * @return 字符串是否为数组串
     */
    public boolean isNumStr(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符串是否为空或者null
     *
     * @param object 对象
     * @return boolean
     */
    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            String value = (String) object;
            return "".equals(value.trim());
        }
        if (object instanceof Collection<?>) {
            Collection<?> list = (Collection<?>) object;
            return list.size() == 0;
        }
        if (object instanceof Object[]) {
            Object[] objs = (Object[]) object;
            return objs.length == 0;
        }
        if (object instanceof Map) {
            Map<?, ?> map = (Map<?, ?>) object;
            return map.size() == 0;
        }
        return false;
    }

    public static boolean isEmptyZero(Object object) {
        if (object == null) {
            return true;
        }
        if (object instanceof String) {
            String value = ((String) object).trim();
            return "".equals(value) || "0".equals(value);
        }
        if (object instanceof Number) {
            return (((Number) object).intValue() == 0);
        }
        if (object instanceof Collection<?>) {
            Collection<?> list = (Collection<?>) object;
            return list.size() == 0;
        }
        if (object instanceof Object[]) {
            Object[] objs = (Object[]) object;
            return objs.length == 0;
        }
        return false;
    }

    /**
     * 删除字符串中的空格
     *
     * @param str 字符串
     * @return 去除空格后的字符串
     */
    public static String removeSpaces(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null && !"".equals(str = str.trim())) {
            StringTokenizer st = new StringTokenizer(str, " ", false);
            while (st.hasMoreElements()) {
                sb.append(st.nextElement());
            }
        }
        return sb.toString();
    }

    /**
     * 获取截取后字符串
     *
     * @param object 对象
     * @return 取截取后字符串
     */
    public static String trimNull(Object object) {
        return trimNull(object, "");
    }

    /**
     * 获取截取后字符串
     *
     * @param object       对象
     * @param defaultValue 默认值
     * @return 取截取后字符串
     */
    public static String trimNull(Object object, String defaultValue) {
        if (object == null) {
            return defaultValue;
        }
        if (object instanceof String) {
            String str = ((String) object).trim();
            if (str.length() == 0) {
                return defaultValue;
            } else {
                return str;
            }
        }
        return object.toString();
    }

    /**
     * 获取截取后字符串
     *
     * @param object 对象
     * @return 取截取后字符串
     */
    public static String trimNullNbsp(Object object) {
        return trimNull(object, "&nbsp;");
    }

    /**
     * 左边填充字符串
     *
     * @param str      字符串
     * @param fillChar 填充字符
     * @param length   长度
     * @return 填充后的字符串
     */
    public static String fillLeft(String str, char fillChar, int length) {
        str = trimNull(str);
        int subLen = str.length();
        StringBuilder rsStr = new StringBuilder();
        for (int i = 0; i < length - subLen; i++) {
            rsStr.append(fillChar);
        }
        rsStr.append(str);
        return rsStr.toString();
    }

    /**
     * 右边填充字符串
     *
     * @param str      字符串
     * @param fillChar 填充字符
     * @param length   长度
     * @return 填充后的字符串
     */
    public static String fillRight(String str, char fillChar, int length) {
        str = trimNull(str);
        int subLen = str.length();
        StringBuilder rsStr = new StringBuilder(str);
        for (int i = 0; i < length - subLen; i++) {
            rsStr.append(String.valueOf(fillChar));
        }
        return rsStr.toString();
    }

    public static String likeTrim(String param) {
        return "%" + StringUtils.trimNull(param) + "%";
    }

    public static String likeLTrim(String param) {
        return "%" + StringUtils.trimNull(param);
    }

    public static String likeRTrim(String param) {
        return StringUtils.trimNull(param) + "%";
    }

    public static String columnToField(String columnName) {
        String[] strs = columnName.toLowerCase().split("_");
        if (strs != null) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < strs.length; i++) {
                String str = strs[i];
                if (i != 0) {
                    str = str.substring(0, 1).toUpperCase() + str.substring(1);
                }
                sb.append(str);
            }
            return sb.toString();
        }
        return columnName;
    }

    public static String fieldToColumn(String fieldName) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < fieldName.length(); i++) {
            char c = fieldName.charAt(i);
            String cStr = String.valueOf(c);
            if (c >= 65 && c <= 95) {
                sb.append("_");
                sb.append(cStr);
            } else {
                sb.append(cStr);
            }
        }
        return sb.toString().toUpperCase();
    }

    public static String[] split(String str, String split) {
        List<String> list = new ArrayList<String>();
        str = StringUtils.trimNull(str);
        if (!StringUtils.isEmpty(str)) {
            String[] strs = str.split(split);
            if (!StringUtils.isEmpty(strs)) {
                for (String string : strs) {
                    string = StringUtils.trimNull(string);
                    if (!StringUtils.isEmpty(string)) {
                        list.add(string);
                    }
                }
            }
        }
        return list.toArray(new String[]{});
    }


    public static String valueOf(Object object, String defaultEmptyValue) {

        if (object == null) {
            return defaultEmptyValue;
        }

        return String.valueOf(object);
    }

    public static String valueOf(Object object) {

        return valueOf(object, "");
    }

    /**
     * 判断对象是否为空
     *
     * @param object
     * @return
     */
    public static boolean isEmptyOrNull(Object object) {


        return StringUtils.valueOf(object).equals("");
    }

    /**
     * 判断对象是否为，如果为空
     *
     * @param object
     * @return
     */
    public static boolean isNotEmptyOrNull(Object object) {
        return !isEmptyOrNull(object);
    }
}
