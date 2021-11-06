package com.rain.common.cache.ehcache;

import com.rain.common.cache.CachePool;
import com.rain.common.cache.CacheStatic;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * @author kunliu
 */
@Slf4j
public class EnchachePool implements CachePool {

    private final Cache enCache;
    private final CacheStatic cacheStatic = new CacheStatic();
    private final String name;
    private final long maxSize;


    public EnchachePool(String name, Cache enCache, long maxSize) {
        this.enCache = enCache;
        this.name = name;
        this.maxSize = maxSize;
        cacheStatic.setMaxSize(this.getMaxSize());

    }

    @Override
    public void putIfAbsent(Object key, Object value) {
        Element element = new Element(key, value);
        if (null == enCache.putIfAbsent(element)) {
            cacheStatic.incPutTimes();
            if (log.isDebugEnabled()) {
                log.debug(name + " add cache ,key: {} value:{}", key, value);
            }
        }

    }

    @Override
    public Object get(Object key) {
        Element cacheEl = enCache.get(key);
        if (cacheEl != null) {
            if (log.isDebugEnabled()) {
                log.debug("{} hit cache ,key:{} ", name, key);
            }
            cacheStatic.incHitTimes();
            return cacheEl.getObjectValue();
        } else {
            if (log.isDebugEnabled()) {
                log.debug("{} miss cache ,key:{}", name, key);
            }
            cacheStatic.incAccessTimes();
            return null;
        }
    }

    @Override
    public void clearCache() {
        log.info("clear cache:{} ", name);
        enCache.removeAll();
        enCache.clearStatistics();
        cacheStatic.reset();
        cacheStatic.setMemorySize(enCache.getMemoryStoreSize());
    }

    @Override
    public CacheStatic getCacheStatic() {
        cacheStatic.setItemSize(enCache.getSize());
        return cacheStatic;
    }

    @Override
    public long getMaxSize() {
        return maxSize;
    }

    @Override
    public boolean delKey(Object key) {
        return enCache.remove(key);
    }

}
