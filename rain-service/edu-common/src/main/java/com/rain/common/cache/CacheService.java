package com.rain.common.cache;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.rain.common.uitls.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
* 缓存服务类
* 源文件名：CacheService.java
* 文件版本：1.0.0
* 创建作者：冰风影
* 创建日期：2016年5月20日
* 修改作者：冰风影
* 修改日期：2016年5月20日
* 版权所有：Copyright 2016 zjhz, Inc. All Rights Reserved.
*/



public class CacheService {
	private static final Logger logger = LoggerFactory.getLogger(CacheService.class);

	private final Map<String, CachePoolFactory> poolFactorys = new HashMap<String, CachePoolFactory>();
	private final Map<String, CachePool> allPools = new HashMap<String, CachePool>();

	private final static CacheService instance=new CacheService();
	
	public final static CacheService getInstance(){
		return instance;
	}
	
	public CacheService() {
		// load cache pool defined
		try {
			init();
		} catch (Exception e) {
			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			} else {
				throw new RuntimeException(e);
			}
		}

	}
	public Map<String, CachePool> getAllCachePools()
	{
		return this.allPools;
	}

	private void init() throws Exception {
		Properties props = new Properties();
		InputStream in= AppUtils.getEnvResource("redis.properties");
		props.load(in);
		final String poolFactoryPref = "factory.";
		final String poolKeyPref = "pool.";
		//final String layedPoolKeyPref = "layedpool.";
		String[] keys = props.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		for (String key : keys) {

			if (key.startsWith(poolFactoryPref)) {
				createPoolFactory(key.substring(poolFactoryPref.length()),
						(String) props.get(key));
			} else if (key.startsWith(poolKeyPref)) {
				String cacheName = key.substring(poolKeyPref.length());
				String value = (String) props.get(key);
				String[] valueItems = value.split(",");
				if (valueItems.length < 3) {
					throw new java.lang.IllegalArgumentException(
							"invalid cache config ,key:" + key + " value:"
									+ value);
				}
				String type = valueItems[0];
				int size = Integer.valueOf(valueItems[1]);
				int timeOut = Integer.valueOf(valueItems[2]);
				createPool(getProviderProperties(props,type),cacheName, type, size, timeOut);
			} 

		}
	}

	private void checkExists(String poolName) {
		if (allPools.containsKey(poolName)) {
			throw new java.lang.IllegalArgumentException(
					"duplicate cache pool name: " + poolName);
		}
	}

	private void createPoolFactory(String factryType, String factryClassName)
			throws Exception {
		CachePoolFactory factry = (CachePoolFactory) Class.forName(
				factryClassName).newInstance();
		poolFactorys.put(factryType, factry);

	}

	private void createPool(Properties props,String poolName, String type, int cacheSize,
			int expireSeconds) {
		checkExists(poolName);
		CachePoolFactory cacheFact = getCacheFact(type);
		CachePool cachePool = cacheFact.createCachePool(props,poolName, cacheSize,
				expireSeconds);
		allPools.put(poolName, cachePool);

	}

	private CachePoolFactory getCacheFact(String type) {
		CachePoolFactory facty = this.poolFactorys.get(type);
		if (facty == null) {
			throw new RuntimeException("CachePoolFactory not defined for type:"
					+ type);
		}
		return facty;
	}

	/**
	 * get cache pool by name ,caller should cache result
	 * 
	 * @param poolName
	 * @return CachePool
	 */
	public CachePool getCachePool(String poolName) {
		CachePool pool = allPools.get(poolName);
		if (pool == null) {
			throw new IllegalArgumentException("can't find cache pool:"
					+ poolName);
		} else {
			return pool;
		}

	}

	public void clearCache() {
		logger.info("clear all cache pool ");
		for (CachePool pool : allPools.values()) {
			pool.clearCache();
		}

	}
	
	public Object get(String region, Object key) {
		return getCachePool(region).get(key);
	}
	
	public void set(String region, Object key,Object value) {
		getCachePool(region).putIfAbsent(key, value);
	}	
	
	public void del(String region, Object key) {
		getCachePool(region).delKey(key);
	}	
	
	public Object get(Object key) {
		return getCachePool("TableKey").get(key);
	}
	
	public void set(Object key,Object value) {
		getCachePool("TableKey").putIfAbsent(key, value);
	}	
	
	public void del( Object key) {
		getCachePool("TableKey").delKey(key);
	}	
	public CacheStatic getCacheStatic(){
		return getCachePool("TableKey").getCacheStatic();
	}
	public void clear( ) {
	}		
	
	public void close( ) {
	}			
	private final static Properties getProviderProperties(Properties props, String cachename) {
		
		Enumeration<Object> keys = props.keys();
		if (keys.hasMoreElements()) {
			Properties new_props = new Properties();
	    	String prefix = cachename + '.';
		    while(keys.hasMoreElements()){
			   String key = (String)keys.nextElement();
			    if(key.startsWith(prefix))
				new_props.setProperty(key.substring(prefix.length()), props.getProperty(key));
	        	}
	    	return new_props;
		}
		else 
			return null;
	}	
}
