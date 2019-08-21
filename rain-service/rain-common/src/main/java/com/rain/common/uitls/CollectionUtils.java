package com.rain.common.uitls;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class CollectionUtils {
    public static List<String> valueOfList(String str) {
        return valueOfList(str, ",");
    }

    public static List<String> valueOfList(String str, String split) {
        List<String> list = new ArrayList<String>();
        if (!StringUtils.isEmpty(str)) {
            String[] strs = str.split(split);
            if (strs.length > 0) {
                for (String string : strs) {
                    list.add(string);
                }
            }
        }
        return list;
    }

    @SuppressWarnings("rawtypes")
    public static Map cloneMap(Map map) {
        Map rsMap = new HashMap();
        Set<Object> set = map.keySet();
        for (Object object : set) {
            Object valueObj = map.get(object);
            if (valueObj != null) {
                if (valueObj instanceof Map) {
                    rsMap.put(object, cloneMap((Map) valueObj));
                } else if (valueObj instanceof List) {
                    rsMap.put(object, cloneList((List) valueObj));
                } else {
                    rsMap.put(object, valueObj);
                }
            } else {
                rsMap.put(object, valueObj);
            }
        }
        return rsMap;
    }

    @SuppressWarnings("rawtypes")
    public static List cloneList(List list) {
        List rsList = new ArrayList();
        for (Object object : list) {
            if (object instanceof List) {
                rsList.add(cloneList((List) object));
            } else if (object instanceof Map) {
                rsList.add(cloneMap((Map) object));
            } else {
                rsList.add(object);
            }
        }
        return rsList;
    }
}
