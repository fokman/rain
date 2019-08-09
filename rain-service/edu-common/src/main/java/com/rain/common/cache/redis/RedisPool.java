package com.rain.common.cache.redis;

import com.rain.common.cache.CachePool;
import com.rain.common.cache.CacheStatic;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.io.IOException;


@Slf4j
public class RedisPool implements CachePool {

    private final CacheStatic cacheStati = new CacheStatic();
    private final String name;
    private final long maxSize;

    public RedisPool(String name, long maxSize) {
        this.name = name;
        this.maxSize = maxSize;
        cacheStati.setMaxSize(this.getMaxSize());
    }

    /**
     * 生成缓存的 key
     *
     * @param key
     * @return
     */
    @SuppressWarnings("rawtypes")
    private String getKeyName(Object key) {
        if (key instanceof Number)
            return this.name + ":I:" + key;
        else {
            Class keyClass = key.getClass();
            if (String.class.equals(keyClass) || StringBuffer.class.equals(keyClass) || StringBuilder.class.equals(keyClass))
                return this.name + ":S:" + key;
        }
        return this.name + ":O:" + key;
    }

    @Override
    public void putIfAbsent(Object key, Object value) {
        if (value == null)
            delKey(key);
        else {
            boolean broken = false;
            Jedis cache = RedisPoolFactory.getResource();
            try {
                cache.set(getKeyName(key).getBytes(), SerializationUtils.serialize(value));
                cacheStati.incPutTimes();
                if (log.isDebugEnabled()) {
                    log.debug("{} add cache ,key:{}， value:{}", name, key, value);
                }
            } catch (Exception e) {
                broken = true;
            } finally {
                RedisPoolFactory.returnResource(cache, broken);
            }
        }

    }

    @Override
    public Object get(Object key) {
        Object obj = null;
        boolean broken = false;
        Jedis cache = RedisPoolFactory.getResource();
        try {
            if (null == key)
                return null;
            byte[] b = cache.get(getKeyName(key).getBytes());
            if (b != null) {
                obj = SerializationUtils.deserialize(b);
                if (log.isDebugEnabled()) {
                    log.debug("{} hit cache ,key:{}", name, key);
                }
                cacheStati.incHitTimes();
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("{} miss cache ,key:{}", name, key);
                }
                cacheStati.incAccessTimes();
            }
        } catch (Exception e) {
            log.error("Error occured when get data from redis cache", e);
            broken = true;
            if (e instanceof IOException || e instanceof NullPointerException)
                delKey(key);
        } finally {
            RedisPoolFactory.returnResource(cache, broken);
        }
        return obj;
    }

    @Override
    public void clearCache() {
        log.info("clear cache {}", name);
        Jedis cache = RedisPoolFactory.getResource();
        boolean broken = false;
        try {
            cache.del(name + ":*");
        } catch (Exception e) {
            broken = true;
        } finally {
            RedisPoolFactory.returnResource(cache, broken);
        }
        cacheStati.reset();
    }

    @Override
    public boolean delKey(Object key) {
        boolean broken = false;
        Jedis cache = RedisPoolFactory.getResource();
        try {
            cache.del(getKeyName(key));
        } catch (Exception e) {
            broken = true;
        } finally {
            RedisPoolFactory.returnResource(cache, broken);
        }
        return !broken;
    }

    @Override
    public CacheStatic getCacheStatic() {
        return cacheStati;
    }

    @Override
    public long getMaxSize() {
        return maxSize;
    }


}
