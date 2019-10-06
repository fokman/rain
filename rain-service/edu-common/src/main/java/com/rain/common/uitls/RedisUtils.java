package com.rain.common.uitls;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by jinggg on 16/5/4.
 */
public class RedisUtils {

	public static final Logger log = LoggerFactory.getLogger(RedisUtils.class);

	private static JedisPool jedisPool = null;

	/**
	 * 初始化连接池
	 */
	static {
		try {
			//ResourceBundle bundle = ResourceBundle.getBundle("redis");
			Properties bundle = new Properties();
			bundle.load(AppUtils.getEnvResource("redis.properties"));
			String address = bundle.getProperty("redis.pool.address");
			Integer port = Integer.valueOf(bundle.getProperty("redis.pool.port"));
			String auth = bundle.getProperty("redis.pool.auth");
			Integer maxActive = Integer.valueOf(bundle.getProperty("redis.pool.maxActive"));
			Integer maxIdle = Integer.valueOf(bundle.getProperty("redis.pool.maxIdle"));
			Integer maxWait = Integer.valueOf(bundle.getProperty("redis.pool.maxWait"));
			Integer timeout = Integer.valueOf(bundle.getProperty("redis.pool.timeout"));
			Boolean testOnBorrow = Boolean.valueOf(bundle.getProperty("redis.pool.testOnBorrow"));
			Boolean testOnReturn = Boolean.valueOf(bundle.getProperty("redis.pool.testOnReturn"));

			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(maxActive);
			config.setMaxIdle(maxIdle);
			config.setMaxWaitMillis(maxWait);
			config.setTestOnBorrow(testOnBorrow);
			config.setTestOnReturn(testOnReturn);

			if (StringUtils.isEmpty(auth)) {
				jedisPool = new JedisPool(config, address, port, timeout);
			} else {
				jedisPool = new JedisPool(config, address, port, timeout, auth);
			}
		} catch (Exception e) {
			log.error("Error thrown while initializing jedis pool", e);
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	private static Jedis getJedis() {
		try {
			return jedisPool.getResource();
		} catch (Exception e) {
			String msg = "Error thrown while getting jedis";
			log.error(msg, e);
			throw new RuntimeException(msg, e);
		}
	}

	/**
	 * jedis实例返还连接池
	 * 
	 * @param jedis
	 */
	private static void closeJedis(Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

	public static void main(String[] args) throws Exception {
		RedisUtils.setex("session_id",5, "zhangjing");
		Thread.sleep(3);
		RedisUtils.expire("session_id",5);
		long start = System.currentTimeMillis();
		while(true){
		   String key = RedisUtils.get("session_id");
		   if(key ==null){
			   break;
		   }
		}
		System.out.println(System.currentTimeMillis()-start);
	}

	public static boolean set(String key, String obj) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			String result = jedis.set(key, obj);
			return "OK".equals(result);
		} catch (Exception e) {
			String msg = "Redis Error thrown while setting key:" + key;
			log.error(msg, e);
			throw new RuntimeException(msg, e);
		} finally {
			closeJedis(jedis);
		}
	}
	public static void remove(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			jedis.del(key);
		} catch (Exception e) {
			String msg = "Error thrown while getting key :" + key;
			log.error(msg, e);
			throw new RuntimeException(msg, e);
		} finally {
			closeJedis(jedis);
		}
	}
	public static String get(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.get(key);
		} catch (Exception e) {
			String msg = "Error thrown while getting key :" + key;
			log.error(msg, e);
			throw new RuntimeException(msg, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	public static List<String> gets(String... keys) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			return jedis.mget(keys);
		} catch (Exception e) {
			String msg = "Error thrown while getting keys :" + keys;
			log.error(msg, e);
			throw new RuntimeException(msg, e);
		} finally {
			closeJedis(jedis);
		}
	}
	public static boolean setSerializeObject(String key, Serializable obj) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			byte[] e = SerializeUtils.serialize(obj);
			if (e == null) {
				return false;
			} else {
				String result = jedis.set(key.getBytes("utf-8"), e);
				return "OK".equals(result);
			}
		} catch (Exception e) {
			String msg = "Error thrown while setSerializeObject key :" + key;
			log.error(msg, e);
			throw new RuntimeException(msg, e);
		} finally {
			closeJedis(jedis);
		}
	}

	public static Object getSerializeObject(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis();
			byte[] e = jedis.get(key.getBytes("utf-8"));
			if (e == null) {
				return null;
			} else {
				return SerializeUtils.unserialize(e);
			}
		} catch (Exception e) {
			String msg = "Error thrown while getSerializeObject key :" + key;
			log.error(msg, e);
			throw new RuntimeException(msg, e);
		} finally {
			closeJedis(jedis);
		}
	}

	public static boolean setex(String key, int seconds, String obj) {
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getJedis();
			String result = jedis.setex(key, seconds, obj);
			return "OK".equals(result);
		} catch (Exception e) {
			String msg = "Error thrown while setexing key :" + key;
			log.error(msg, e);
			throw new RuntimeException(msg, e);
		} finally {
			closeJedis(jedis);
		}
	}

	public static void expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = RedisUtils.getJedis();
			 jedis.expire(key, seconds);
		} catch (Exception e) {
			String msg = "Error thrown while expire key :" + key;
			log.error(msg, e);
			throw new RuntimeException(msg, e);
		} finally {
			closeJedis(jedis);
		}
	}
	
	 

}
