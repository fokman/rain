package com.rain.common.validation;

import com.rain.common.core.Interceptor;
import com.rain.common.exception.ValidationException;
import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceRespose;
import com.rain.common.validation.support.ValidationSupport;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class ValidInterceptor implements Interceptor {

    @Override
    public boolean preHandle(Object iceCls, Method method, IceRequest iceRequest, IceRespose iceRespose) {
        String serviceId = iceRequest.getService();
        String methodId = iceRequest.getMethod();
        log.info("validate start {} {} ", serviceId, methodId);
        try {
            ValidationSupport.doValid(serviceId, methodId, iceRequest);
        } catch (ValidationException e) {
            //iceRespose = new IceRespose();
            iceRespose.setCode(302, e.getMessage());
            log.error(iceRespose.getMsg());
            return false;
        }
        log.info("validate end {} {} ", serviceId, methodId);
        return true;
    }

    @Override
    public void afterHandle(Object iceCls, Method method,
                            IceRequest iceRequest, IceRespose iceRespose) {
        // TODO Auto-generated method stub

    }

}
