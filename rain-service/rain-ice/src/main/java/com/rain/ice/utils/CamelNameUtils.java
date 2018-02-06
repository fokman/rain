package com.rain.ice.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

public class CamelNameUtils {

    public static String toUnderlineName(String camelName) {
        camelName = capitalize(camelName);

        String regex = "([A-Z][a-z]+)";
        String replacement = "$1_";

        String underscoreName = camelName.replaceAll(regex, replacement);

        underscoreName = underscoreName.toLowerCase().substring(0, underscoreName.length() - 1);

        return underscoreName;
    }

    public static String toCamelCase(String underscoreName) {
        String[] sections = underscoreName.split("_");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < sections.length; i++) {
            String s = sections[i];
            if (i == 0) {
                sb.append(s);
            } else {
                sb.append(capitalize(s));
            }
        }
        return sb.toString();
    }


    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen)
                .append(Character.toTitleCase(str.charAt(0)))
                .append(str.substring(1))
                .toString();
    }

    public static Map<String, Object> mapToCamel(Map<String, Object> data) {
        Map<String, Object> rowMap = new HashMap<String, Object>();
        if (data != null && !data.isEmpty()) {
            Set set = data.keySet();
            for (Iterator iter = set.iterator(); iter.hasNext(); ) {
                String key = (String) iter.next();
                if (key.endsWith("_id") || key.endsWith("_user")) {//字段以_id结尾的转字符串
                    rowMap.put(CamelNameUtils.toCamelCase(key), String.valueOf(data.get(key)));
                } else {//日期类型格式化
                    Object parameter = data.get(key);
                    if (parameter instanceof java.util.Date) {
                        rowMap.put(CamelNameUtils.toCamelCase(key), DateUtils.formatTime(parameter));
                    } else if (parameter instanceof Date) {
                        rowMap.put(CamelNameUtils.toCamelCase(key), DateUtils.formatTime(parameter));
                    } else if (parameter instanceof Timestamp) {
                        rowMap.put(CamelNameUtils.toCamelCase(key), DateUtils.formatTime(parameter));
                    } else {
                        rowMap.put(CamelNameUtils.toCamelCase(key), data.get(key));
                    }
                }
            }
        }
        return rowMap;
    }

    public static List<Map<String, Object>> listToCamel(List<Map<String, Object>> datas) {
        List<Map<String, Object>> rows = new ArrayList<>();
        for (Map<String, Object> data : datas) {
            rows.add(mapToCamel(data));
        }
        return rows;
    }

}

