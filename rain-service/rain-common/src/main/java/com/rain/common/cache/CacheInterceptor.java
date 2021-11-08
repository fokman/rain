package com.rain.common.cache;

import cn.hutool.json.JSONUtil;
import com.rain.common.cache.annotation.Cache;
import com.rain.common.cache.annotation.CacheTypeEnum;
import com.rain.common.core.Interceptor;
import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceRespose;
import com.rain.common.uitls.JsonUtils;
import com.rain.common.uitls.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;

@Slf4j
public class CacheInterceptor implements Interceptor {

    @Override
    public boolean preHandle(Object iceCls, Method method,
                             IceRequest iceRequest, IceRespose iceRespose) {
        long begin = System.currentTimeMillis();
        Cache cache = method.getAnnotation(Cache.class);
        if (cache != null) {
            if (cache.type() == CacheTypeEnum.ADD) {
                //读缓存
                String cacheKey = CacheUtils.getKey(cache, iceRequest);
                String value = RedisUtils.get(cacheKey);
                if (StringUtils.isNotEmpty(value)) {
                    copyIceRespose(iceRespose, JsonUtils.toObject(value, IceRespose.class));
                    log.info("hit cache key:[{}] call:[{}.{}] to spend:{}ms", cacheKey, iceRequest.getService(),
                            iceRequest.getMethod(), System.currentTimeMillis() - begin);
                    return false;
                }
            }
        }
        return true;
    }

    private void copyIceRespose(IceRespose iceRespose, IceRespose newiceRespose) {
        iceRespose.setCode(newiceRespose.getCode());
        iceRespose.setMsg(newiceRespose.getMsg());
        iceRespose.setTotal(newiceRespose.getTotal());
        iceRespose.setData(newiceRespose.getData());
    }

    @Override
    public void afterHandle(Object iceCls, Method method,
                            IceRequest iceRequest, IceRespose iceRespose) {
        long begin = System.currentTimeMillis();
        Cache cache = method.getAnnotation(Cache.class);
        String cacheKey = "";
        if (StringUtils.isNotEmpty(cacheKey)) {
            cacheKey = CacheUtils.getKey(cache, iceRequest);
            if (cache.type() == CacheTypeEnum.DEL) {
                //删除缓存
                RedisUtils.remove(cacheKey);
                log.info("remove cache key:[{}] call:[{}.{}] to spend:{}ms", cacheKey, iceRequest.getService(), iceRequest.getMethod(), System.currentTimeMillis() - begin);
            }
            //写缓存
            if (cache.type() == CacheTypeEnum.ADD) {
                String json = JsonUtils.toJson(iceRespose);
                if (cache.seconds() == -1) {
                    RedisUtils.set(cacheKey, json);
                } else {
                    RedisUtils.setex(cacheKey, cache.seconds(), json);
                }
            }
        }

    }

}
