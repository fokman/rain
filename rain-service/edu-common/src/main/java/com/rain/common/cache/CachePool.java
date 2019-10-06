package com.rain.common.cache;

public interface CachePool {

	public void putIfAbsent(Object key, Object value);

	public Object get(Object key);

	public boolean delKey(Object key);
	
	public void clearCache();

	public CacheStatic getCacheStatic();

	public long getMaxSize();
}