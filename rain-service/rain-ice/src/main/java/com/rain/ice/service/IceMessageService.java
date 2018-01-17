package com.rain.ice.service;

import Ice.Current;
import com.rain.ice.message.MsgRequest;
import com.rain.ice.message._MessageServiceDisp;
import com.rain.ice.model.IceRequest;
import com.rain.ice.model.IceResponse;
import com.rain.ice.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/11
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class IceMessageService extends _MessageServiceDisp {

    private Logger logger = LoggerFactory.getLogger(IceMessageService.class);

    @Override
    public String doInvoke(MsgRequest msgRequest, Current __current) {
        logger.info("invoke msgRequest:{}",msgRequest);
        String service = msgRequest.service;
        String method = msgRequest.method;
        Object iceClazz;
        if (!StringUtils.isEmpty(service)) {
            iceClazz = IceServiceRegister.getInstance().getService(service);
//                iceClazz = Class.forName(service).newInstance();
        } else {
            throw new RuntimeException("无效的服务名:" + service);
        }
        Method m = null;
        if (method != null && method.length() != 0) {
            try {
                m = iceClazz.getClass().getMethod(method, IceRequest.class);
            } catch (NoSuchMethodException e) {
                logger.error("{}",e);
            }
        }
        IceRequest iceRequest = new IceRequest();
        iceRequest.setService(service);
        iceRequest.setMethod(method);
        if (msgRequest.extraData != null) {
            iceRequest.getExtraData().putAll(msgRequest.extraData);
        }
        if (msgRequest.attr != null) {
            iceRequest.getAttr().putAll(msgRequest.attr);
        }

        IceResponse iceResponse = new IceResponse();
        try {
            if (m!=null) {
                iceResponse = (IceResponse) m.invoke(iceClazz, iceRequest);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("{}",e);
        }
        return JsonUtils.toJson(iceResponse);
    }
}
