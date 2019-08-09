package com.rain.common.cache;

public interface CachePool {

    void putIfAbsent(Object key, Object value);

    Object get(Object key);

    boolean delKey(Object key);

    void clearCache();

    CacheStatic getCacheStatic();

    long getMaxSize();
}