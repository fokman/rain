package com.rain.common.cache.redis;

import java.io.IOException;

import com.rain.common.cache.CachePool;
import com.rain.common.cache.CacheStatic;
import com.rain.common.cache.util.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
* 缓存redis类
* 源文件名：RedisPool.java
* 文件版本：1.0.0
* 创建作者：冰风影
* 创建日期：2016年5月20日
* 修改作者：冰风影
* 修改日期：2016年5月20日
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
*/

public class RedisPool implements CachePool {
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisPool.class);

	private final CacheStatic cacheStati = new CacheStatic();
    private final String name;
    private final long maxSize;

	public RedisPool(String name,long maxSize) {
		this.name=name;
		this.maxSize=maxSize;
		cacheStati.setMaxSize(this.getMaxSize());
	}
	
	/**
	 * 生成缓存的 key
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private String getKeyName(Object key) {
		if(key instanceof Number)
			return this.name + ":I:" + key;
		else{
			Class keyClass = key.getClass();
			if(String.class.equals(keyClass) || StringBuffer.class.equals(keyClass) || StringBuilder.class.equals(keyClass))
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
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(name+" add cache ,key:" + key + " value:" + value);
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
			if(b != null) {
				obj = SerializationUtils.deserialize(b);
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(name+" hit cache ,key:" + key);
				}				
			    cacheStati.incHitTimes();
		 	}
			else {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug(name+"  miss cache ,key:" + key);
				}				
				cacheStati.incAccessTimes();
			}
		} catch (Exception e) {
			LOGGER.error("Error occured when get data from redis cache", e);
			broken = true;
			if(e instanceof IOException || e instanceof NullPointerException)
				delKey(key);
		} finally {
			RedisPoolFactory.returnResource(cache, broken);
		}
		return obj;
	}

	@Override
	public void clearCache() {
		LOGGER.info("clear cache "+name);
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
	//	cacheStati.setMemorySize();		
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
	//	cacheStati.setItemSize(enCache.getSize());
		return cacheStati;
	}

	@Override
	public long getMaxSize() {
		return maxSize;
	}


	
}
