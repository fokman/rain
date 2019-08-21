package com.rain.common.cache;

import com.rain.common.cache.annotation.Cache;
import com.rain.common.ice.model.IceRequest;
import org.apache.commons.lang3.StringUtils;

public class CacheUtils {
    private static final String PREFIX_LINKER = "$";
    private static final String NAME_LINKER = ".";

    public static String getKey(Cache cache, IceRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(cache.prefix());
        sb.append(PREFIX_LINKER);
        String[] paramItems = cache.params();
        for (String paramItem : paramItems) {
            String param = request.getAttr(paramItem);
            if (StringUtils.isNotBlank(param)) {
                sb.append(param.trim());
                sb.append(NAME_LINKER);
            }
        }
        return sb.toString();
    }
}
