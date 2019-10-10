package com.rain.common.core;

import java.lang.reflect.Method;

import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceRespose;


public class HandlerExecution {

    private Interceptor[] interceptors;
    private int interceptorIndex = -1;

    public HandlerExecution(Interceptor... interceptors) {
        this.interceptors = interceptors;
    }

    public Interceptor[] getInterceptors() {
        return this.interceptors;
    }

    private boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    boolean applyPreHandle(Object iceCls, Method method, IceRequest iceRequest, IceRespose iceRespose) throws Exception {
        Interceptor[] interceptors = getInterceptors();
        if (!isEmpty(interceptors)) {
            for (int i = 0; i < interceptors.length; i++) {
                Interceptor interceptor = interceptors[i];
                if (!interceptor.preHandle(iceCls, method, iceRequest, iceRespose)) {
                    return false;
                }
                this.interceptorIndex = i;
            }
        }
        return true;
    }

    void applyAfterHandle(Object iceCls, Method method, IceRequest iceRequest, IceRespose iceRespose) throws Exception {
        Interceptor[] interceptors = getInterceptors();
        if (!isEmpty(interceptors)) {
            for (int i = interceptors.length - 1; i >= 0; i--) {
                Interceptor interceptor = interceptors[i];
                interceptor.afterHandle(iceCls, method, iceRequest, iceRespose);
            }
        }
    }

    
}
