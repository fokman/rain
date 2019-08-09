package com.rain.common.cache.ehcache;

import com.rain.common.cache.CachePool;
import com.rain.common.cache.CacheStatic;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;


@Slf4j
public class EnchachePool implements CachePool {
    private final Cache enCache;
    private final CacheStatic cacheStati = new CacheStatic();
    private final String name;
    private final long maxSize;

    public EnchachePool(String name, Cache enCache, long maxSize) {
        this.enCache = enCache;
        this.name = name;
        this.maxSize = maxSize;
        cacheStati.setMaxSize(this.getMaxSize());

    }

    @Override
    public void putIfAbsent(Object key, Object value) {
        Element el = new Element(key, value);
        if (enCache.putIfAbsent(el) == null) {
            cacheStati.incPutTimes();
            if (log.isDebugEnabled()) {
                log.debug(" {} add cache ,key:{}", name, value);
            }
        }

    }

    @Override
    public Object get(Object key) {
        Element cacheEl = enCache.get(key);
        if (cacheEl != null) {
            if (log.isDebugEnabled()) {
                log.debug(" {} hit cache ,key:{}", name, key);
            }
            cacheStati.incHitTimes();
            return cacheEl.getObjectValue();
        } else {
            if (log.isDebugEnabled()) {
                log.debug(name + " {} miss cache ,key:{}", name, key);
            }
            cacheStati.incAccessTimes();
            return null;
        }
    }

    @Override
    public void clearCache() {
        log.info("clear cache " + name);
        enCache.removeAll();
        enCache.clearStatistics();
        cacheStati.reset();
        cacheStati.setMemorySize(enCache.getMemoryStoreSize());
    }

    @Override
    public CacheStatic getCacheStatic() {
        cacheStati.setItemSize(enCache.getSize());
        return cacheStati;
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