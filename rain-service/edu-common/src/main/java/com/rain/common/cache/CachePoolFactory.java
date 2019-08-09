package com.rain.common.cache;

import java.util.Properties;

public abstract class CachePoolFactory {

	/**
	 *
	 * @param props
	 * @param poolName
	 * @param cacheSize
	 * @param expireSeconds
	 * @return
	 */
	public abstract CachePool createCachePool(Properties props,String poolName,int cacheSize,int expireSeconds);
}