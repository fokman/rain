package com.rain.common.core;

import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceResponse;

import java.lang.reflect.Method;

public interface Interceptor {
    boolean preHandle(Object iceCls, Method method, IceRequest iceRequest, IceResponse iceRespose);

    void afterHandle(Object iceCls, Method method, IceRequest iceRequest, IceResponse iceRespose);
}
