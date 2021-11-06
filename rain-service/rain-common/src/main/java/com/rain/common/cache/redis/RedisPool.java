package com.rain.common.cache.redis;

import com.rain.common.cache.CachePool;
import com.rain.common.cache.CacheStatic;
import com.rain.common.cache.util.SerializationUtils;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.io.IOException;

/**
 * @author kunliu
 */
@Slf4j
public class RedisPool implements CachePool {
    private final CacheStatic cacheStatic = new CacheStatic();
    private final String name;
    private final long maxSize;

    public RedisPool(String name, long maxSize) {
        this.name = name;
        this.maxSize = maxSize;
        cacheStatic.setMaxSize(this.getMaxSize());
    }

    /**
     * 生成缓存的 key
     *
     * @param key
     * @return
     */
    private String getKeyName(Object key) {
        if (key instanceof Number) {
            return this.name + ":I:" + key;
        } else {
            Class keyClass = key.getClass();
            if (String.class.equals(keyClass) || StringBuffer.class.equals(keyClass)
                    || StringBuilder.class.equals(keyClass)) {
                return this.name + ":S:" + key;
            }
        }
        return this.name + ":O:" + key;
    }

    @Override
    public void putIfAbsent(Object key, Object value) {
        if (value == null) {
            delKey(key);
        } else {
            boolean broken = false;
            Jedis cache = RedisPoolFactory.getResource();
            try {
                cache.set(getKeyName(key).getBytes(), SerializationUtils.serialize(value));
                cacheStatic.incPutTimes();
                if (log.isDebugEnabled()) {
                    log.debug(name + " {} add cache ,key:{} value:{}", name, key, value);
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
            if (null == key) {
                return null;
            }
            byte[] b = cache.get(getKeyName(key).getBytes());
            if (b != null) {
                obj = SerializationUtils.deserialize(b);
                if (log.isDebugEnabled()) {
                    log.debug(name + " hit cache ,key:" + key);
                }
                cacheStatic.incHitTimes();
            } else {
                if (log.isDebugEnabled()) {
                    log.debug(name + "  miss cache ,key:" + key);
                }
                cacheStatic.incAccessTimes();
            }
        } catch (Exception e) {
            log.error("Error occured when get data from redis cache", e);
            broken = true;
            if (e instanceof IOException || e instanceof NullPointerException) {
                delKey(key);
            }

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
        cacheStatic.reset();
    }

    @Override
    public boolean delKey(Object key) {
        boolean broken = false;
        Jedis cache = RedisPoolFactory.getResource();
        //cache.dbSize();
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
        return cacheStatic;
    }

    @Override
    public long getMaxSize() {
        return maxSize;
    }


}
