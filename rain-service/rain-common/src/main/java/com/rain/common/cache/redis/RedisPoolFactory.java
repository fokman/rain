package com.rain.common.cache.redis;

import com.rain.common.cache.CachePool;
import com.rain.common.cache.CachePoolFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

/**
 * @author kunliu
 */
@Slf4j
public class RedisPoolFactory extends CachePoolFactory {
    private static JedisPool pool = null;

    @Override
    public CachePool createCachePool(Properties props, String poolName, int cacheSize,
                                     int expireSeconds) {
        if (pool == null) {
            try {
                String address = props.getProperty("pool.address");
                Integer port = Integer.valueOf(props.getProperty("pool.port"));
                String auth = props.getProperty("pool.auth");
                Integer maxActive = Integer.valueOf(props.getProperty("pool.maxActive"));
                Integer maxIdle = Integer.valueOf(props.getProperty("pool.maxIdle"));
                Integer maxWait = Integer.valueOf(props.getProperty("pool.maxWait"));
                Integer timeout = Integer.valueOf(props.getProperty("pool.timeout"));
                Boolean testOnBorrow = Boolean.valueOf(props.getProperty("pool.testOnBorrow"));
                Boolean testOnReturn = Boolean.valueOf(props.getProperty("pool.testOnReturn"));

                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(maxActive);
                config.setMaxIdle(maxIdle);
                config.setMaxWaitMillis(maxWait);
                config.setTestOnBorrow(testOnBorrow);
                config.setTestOnReturn(testOnReturn);

                if (StringUtils.isEmpty(auth)) {
                    pool = new JedisPool(config, address, port, timeout);
                } else {
                    pool = new JedisPool(config, address, port, timeout, auth);
                }
            } catch (Exception e) {
                log.error("Error Error thrown while initializing jedis pool", e);
            }
        }
        return new RedisPool(poolName, cacheSize);
    }

    private static String getProperty(Properties props, String key, String defaultValue) {
        return props.getProperty(key, defaultValue).trim();
    }

    private static int getProperty(Properties props, String key, int defaultValue) {
        try {
            return Integer.parseInt(props.getProperty(key, String.valueOf(defaultValue)).trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    private static boolean getProperty(Properties props, String key, boolean defaultValue) {
        return "true".equalsIgnoreCase(props.getProperty(key, String.valueOf(defaultValue)).trim());
    }

    /**
     * 释放资源
     *
     * @param jedis            jedis instance
     * @param isBrokenResource resource is ok or not
     */
    public static void returnResource(Jedis jedis, boolean isBrokenResource) {
        if (null == jedis)
            return;
        if (jedis != null) {
            jedis.close();
        }
		/*
        if(isBrokenResource){     
        	pool.returnBrokenResource(jedis);
            jedis = null;
        }
        else
        	pool.returnResource(jedis);
    }
    */
    }

    public static Jedis getResource() {
        return pool.getResource();
    }
}
