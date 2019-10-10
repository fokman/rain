package com.rain.common.cache;

import com.rain.common.cache.annotation.Cache;
import com.rain.common.ice.v1.model.IceRequest;
import org.apache.commons.lang3.StringUtils;

public class CacheUtils {
    private static final String PREFIX_LINKER = "$";
    private static final String NAME_LINKER = ".";

    public static String getKey(Cache cache, IceRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append(cache.prefix());
        sb.append(PREFIX_LINKER);
        for (int i = 0; i < cache.params().length; i++) {
            String pattern = cache.params()[i];
            String param = request.getAttr(pattern);
            if (StringUtils.isNotEmpty(param)) {
                sb.append(param.trim());
                sb.append(NAME_LINKER);
            }
        }
        return sb.toString();
    }
}
