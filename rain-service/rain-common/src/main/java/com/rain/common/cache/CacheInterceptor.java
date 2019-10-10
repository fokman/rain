package com.rain.common.cache;

import java.lang.reflect.Method;

import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceRespose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rain.common.cache.annotation.Cache;
import com.rain.common.cache.annotation.CacheTypeEnum;
import com.rain.common.core.Interceptor;
import com.rain.common.uitls.JsonUtils;
import com.rain.common.uitls.RedisUtils;

public class CacheInterceptor  implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(CacheInterceptor.class);
	
	@Override
	public boolean preHandle(Object iceCls, Method method,
                             IceRequest iceRequest, IceRespose iceRespose) {
		long begin = System.currentTimeMillis();
		Cache cache=method.getAnnotation(Cache.class);
		String cacheKey="";
		if (cache != null) {			
			if (cache.type()==CacheTypeEnum.ADD){
			  //读缓存
			cacheKey = CacheUtils.getKey(cache,iceRequest);
			  String value=RedisUtils.get(cacheKey);
			  if (value !=null && value !=""){
				  copyIceRespose(iceRespose,JsonUtils.toObject(value, IceRespose.class));
				  logger.info("hit cache key:[{}] call:[{}.{}] to spend:{}ms",cacheKey, iceRequest.getService(), iceRequest.getMethod(), System.currentTimeMillis() - begin);  
				return false;//value;
			  }
			}
		}
		return true;
	}
    private void copyIceRespose(IceRespose iceRespose,IceRespose newiceRespose){
    	iceRespose.setCode(newiceRespose.getCode());
    	iceRespose.setMsg(newiceRespose.getMsg());
    	iceRespose.setTotal(newiceRespose.getTotal());
    	iceRespose.setData(newiceRespose.getData());
    }
	@Override
	public void afterHandle(Object iceCls, Method method,
			IceRequest iceRequest, IceRespose iceRespose) {	
		long begin = System.currentTimeMillis();
		Cache cache=method.getAnnotation(Cache.class);		
		String cacheKey="";		
		if (cache != null) {
			cacheKey = CacheUtils.getKey(cache,iceRequest);
			if (cache.type()==CacheTypeEnum.DEL){
				//删除缓存
				RedisUtils.remove(cacheKey);
				logger.info("remove cache key:[{}] call:[{}.{}] to spend:{}ms",cacheKey, iceRequest.getService(), iceRequest.getMethod(), System.currentTimeMillis() - begin);  
			}				
			//写缓存
			if (cache.type()==CacheTypeEnum.ADD){
				String json=JsonUtils.toJson(iceRespose);
				if (cache.seconds() == -1) {
				  RedisUtils.set(cacheKey,  json);
				}
				else {
				  RedisUtils.setex(cacheKey, cache.seconds(), json);
				}
			}				
		}
		
	}

}
