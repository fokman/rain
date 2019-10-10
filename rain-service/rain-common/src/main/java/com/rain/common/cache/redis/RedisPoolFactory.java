package com.rain.common.cache.redis;

import com.rain.common.cache.CachePool;
import com.rain.common.cache.CachePoolFactory;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

public class RedisPoolFactory extends CachePoolFactory {
	//private static String host;
	//private static int port;
	//private static int timeout;
	//private static String password;
	//private static int database;
	
	private static JedisPool pool=null;
	@Override
	public CachePool createCachePool(Properties props, String poolName, int cacheSize,
                                     int expireSeconds) {
		if (pool==null) {
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
			//log.error("Error thrown while initializing jedis pool", e);
		  }		
		}
		/*
		if (pool==null) {
				JedisPoolConfig config = new JedisPoolConfig();
				
				host = getProperty(props, "pool.address","127.0.0.1");
				password = props.getProperty("pool.password", null);
				
				port = getProperty(props, "pool.port", 6379);
				timeout = getProperty(props, "pool.timeout", 2000);
				//database = getProperty(props, "database", 0);
				
				//config.setWhenExhaustedAction((byte)getProperty(props, "whenExhaustedAction",1));
				config.setMaxIdle(getProperty(props, "pool.maxIdle", 10));
				config.setMinIdle(getProperty(props, "pool.minIdle", 5));
				//config.setMaxActive(getProperty(props, "maxActive", 50));
				//config.setMaxWait(getProperty(props, "maxWait", 100));
				config.setTestWhileIdle(getProperty(props, "testWhileIdle", false));
				config.setTestOnBorrow(getProperty(props, "pool.testOnBorrow", true));
				config.setTestOnReturn(getProperty(props, "pool.testOnReturn", false));
				config.setNumTestsPerEvictionRun(getProperty(props, "pool.numTestsPerEvictionRun", 10));
				config.setMinEvictableIdleTimeMillis(getProperty(props, "pool.minEvictableIdleTimeMillis", 1000));
				config.setSoftMinEvictableIdleTimeMillis(getProperty(props, "pool.softMinEvictableIdleTimeMillis", 10));
				config.setTimeBetweenEvictionRunsMillis(getProperty(props, "pool.timeBetweenEvictionRunsMillis", 10));
				//config.lifo = getProperty(props, "lifo", false);
				
				//pool = new JedisPool(config, host, port, timeout, password, database);
				pool = new JedisPool(config, host, port, timeout, password);
		}
		*/
		return new RedisPool(poolName,cacheSize);
	}
	private static String getProperty(Properties props, String key, String defaultValue) {
		return props.getProperty(key, defaultValue).trim();
	}

	private static int getProperty(Properties props, String key, int defaultValue) {
		try{
			return Integer.parseInt(props.getProperty(key, String.valueOf(defaultValue)).trim());
		}catch(Exception e){
			return defaultValue;
		}
	}

	private static boolean getProperty(Properties props, String key, boolean defaultValue) {
		return "true".equalsIgnoreCase(props.getProperty(key, String.valueOf(defaultValue)).trim());
	}
	
	/**
	 * 释放资源
	 * @param jedis  jedis instance
	 * @param isBrokenResource resource is ok or not
	 */
    public static void returnResource(Jedis jedis,boolean isBrokenResource) {
    	if(null == jedis)
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
