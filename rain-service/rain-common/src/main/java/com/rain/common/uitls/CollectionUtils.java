package com.rain.common.uitls;

import com.rain.common.exception.ServiceException;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public class CollectionUtils {
    public static List<String> valueOfList(String str) {
        return valueOfList(str, ",");
    }

    public static List<String> valueOfList(String str, String split) {
        str = StringUtils.trimNull(str);
        if (StringUtils.isEmpty(str)) {
            throw new ServiceException("str is null");
        }
        String[] strs = str.split(split);
        List<String> result = Arrays.stream(strs).filter(string -> !StringUtils.isEmpty(string))
                .collect(Collectors.toList());
        return result;
    }

   /* @SuppressWarnings("rawtypes")
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
    }*/
}
