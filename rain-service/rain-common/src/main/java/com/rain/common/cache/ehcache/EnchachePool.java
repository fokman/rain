package com.rain.common.cache.ehcache;

import com.rain.common.cache.CachePool;
import com.rain.common.cache.CacheStatic;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* 缓存Enchache类
* 源文件名：EnchachePool.java
* 文件版本：1.0.0
* 创建作者：冰风影
* 创建日期：2016年5月20日
* 修改作者：冰风影
* 修改日期：2016年5月20日
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
*/

public class EnchachePool implements CachePool {
	private static final Logger LOGGER = LoggerFactory.getLogger(EnchachePool.class);
	private final Cache enCache;
	private final CacheStatic cacheStati = new CacheStatic();
    private final String name;
    private final long maxSize;
	public EnchachePool(String name,Cache enCache,long maxSize) {
		this.enCache = enCache;
		this.name=name;
		this.maxSize=maxSize;
		cacheStati.setMaxSize(this.getMaxSize());

	}

	@Override
	public void putIfAbsent(Object key, Object value) {
		Element el = new Element(key, value);
		if (enCache.putIfAbsent(el) == null) {
			cacheStati.incPutTimes();
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(name+" add cache ,key:" + key + " value:" + value);
			}
		}

	}

	@Override
	public Object get(Object key) {
		Element cacheEl = enCache.get(key);
		if (cacheEl != null) {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(name+" hit cache ,key:" + key);
			}
			cacheStati.incHitTimes();
			return cacheEl.getObjectValue();
		} else {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug(name+"  miss cache ,key:" + key);
			}
			cacheStati.incAccessTimes();
			return null;
		}
	}

	@Override
	public void clearCache() {
		LOGGER.info("clear cache "+name);
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