package com.rain.common.core;

import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceRespose;

import java.lang.reflect.Method;

public interface Interceptor {
	boolean preHandle(Object iceCls, Method method, IceRequest iceRequest, IceRespose iceRespose);
	void afterHandle(Object iceCls,Method method,IceRequest iceRequest,IceRespose iceRespose);
}
