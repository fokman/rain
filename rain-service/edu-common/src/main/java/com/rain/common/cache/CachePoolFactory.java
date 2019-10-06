package com.rain.common.cache;

import java.util.Properties;

public abstract class CachePoolFactory {

	/**
	 *  create a cache pool instance
	 * @param poolName
	 * @param cacheSize
	 * @param expireSeconds -1 for not expired
	 * @return
	 */
	public abstract CachePool createCachePool(Properties props,String poolName,int cacheSize,int expireSeconds);
}