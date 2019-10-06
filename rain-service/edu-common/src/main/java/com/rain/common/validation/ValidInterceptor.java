package com.rain.common.validation;

import java.lang.reflect.Method;

import com.rain.common.core.Interceptor;
import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceRespose;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rain.common.exception.ValidationException;
import com.rain.common.validation.support.ValidationSupport;

public class ValidInterceptor implements Interceptor {
	private static final Logger logger = LoggerFactory.getLogger(ValidInterceptor.class);
	
	@Override
	public boolean preHandle(Object iceCls, Method method, IceRequest iceRequest, IceRespose iceRespose) {
		String serviceId   = iceRequest.getService();
		String methodId = iceRequest.getMethod();
        logger.info("validate start {} {} ",serviceId,methodId);
		try {
			ValidationSupport.doValid(serviceId, methodId, iceRequest);
		} catch (ValidationException e) {
			//iceRespose = new IceRespose();
			iceRespose.setCode(302,e.getMessage());
			logger.error(iceRespose.getMsg());
			return false;
		}
		logger.info("validate end {} {} ",serviceId,methodId);
		return true;
	}

	@Override
	public void afterHandle(Object iceCls, Method method,
			IceRequest iceRequest, IceRespose iceRespose) {
		// TODO Auto-generated method stub
		
	}

}
