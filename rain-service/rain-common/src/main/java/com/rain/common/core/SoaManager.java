package com.rain.common.core;

import com.rain.common.cache.CacheInterceptor;
import com.rain.common.dao.TxUtils;
import com.rain.common.ice.v1.impl.IceServiceRegister;
import com.rain.common.ice.v1.message.MsgRequest;
import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceRespose;
import com.rain.common.servcie.config.Transactional;
import com.rain.common.stat.StatAnalyzer;
import com.rain.common.translate.TransInterceptor;
import com.rain.common.uitls.JsonUtils;
import com.rain.common.validation.ValidInterceptor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

@Slf4j
public class SoaManager {
    private volatile static SoaManager soaManager;

    private static final IceServiceRegister register = IceServiceRegister.getInstance();
    private HandlerExecution handlerExecution;

    private SoaManager() {
        handlerExecution = new HandlerExecution(new ValidInterceptor(), new CacheInterceptor(), new TransInterceptor());
    }

    public static SoaManager getInstance() {
        if (soaManager == null) {
            synchronized (SoaManager.class) {
                if (soaManager == null) {
                    soaManager = new SoaManager();
                }
            }
        }
        return soaManager;
    }

    private IceRespose returnRespose(Integer code, String msg) {
        IceRespose iceRespose = new IceRespose();
        iceRespose.setCode(code);
        iceRespose.setMsg(msg);
        log.error(iceRespose.getMsg());
        return iceRespose;
    }

    public IceRespose doInvoke(MsgRequest msgRequest) {
    	long begin = System.currentTimeMillis();
        String serviceId = msgRequest.service;
        String methodId = msgRequest.method;
        try {           
            log.info("service {} {} begin", serviceId, methodId);
            String key = serviceId;
            Object iceCls = null;
            if (serviceId != null && serviceId.length() != 0) {
                iceCls = register.getService(key);
            }
            if (iceCls == null) {
                return returnRespose(300, "无效的服务名  serverId:" + serviceId);//JsonUtils.toJson(iceRespose);
            }
            Method method = null;
            if (methodId != null && methodId.length() != 0) {
                try {
                    method = iceCls.getClass().getMethod(methodId, IceRequest.class);
                } catch (Throwable e) {
                    return returnRespose(301, "无效的方法名 methodId :" + serviceId + "." + methodId);
                }
            }
            if (method == null) {
                return returnRespose(301, "无效的方法名 methodId :" + serviceId + "." + methodId);//JsonUtils.toJson(iceRespose);
            }
            IceRequest iceRequest = new IceRequest();
            iceRequest.setService(serviceId);
            iceRequest.setMethod(methodId);
            if (msgRequest.extData!= null) {
                iceRequest.getExtData().putAll(msgRequest.extData);
            }
            if (msgRequest.attr != null) {
                iceRequest.getAttr().putAll(msgRequest.attr);
            }
            log.debug("doInvoke -->iceRequest = {}", iceRequest.getAttrMap().toString());
            IceRespose iceRespose = new IceRespose();
            //启用拦截器
            if (!handlerExecution.applyPreHandle(iceCls, method, iceRequest, iceRespose)) {
                return iceRespose;
            }

            log.info("invoke start {} {} ", serviceId, methodId);
            Transactional[] ts = method.getAnnotationsByType(Transactional.class);
            if (ts != null && ts.length > 0) {
                try {
                    TxUtils.beginTransaction();
                    iceRespose = (IceRespose) method.invoke(iceCls, iceRequest);
                    TxUtils.commit();
                } catch (Throwable t) {
                    TxUtils.rollback();
                    throw t;
                } finally {
                    TxUtils.endTransaction();
                }
            } else {
                iceRespose = (IceRespose) method.invoke(iceCls, iceRequest);
            }
            log.info("invoke end {} {} ", serviceId, methodId);
            if (iceRespose.getCode() == 0) {
                //执行成功在启用拦截器
                handlerExecution.applyAfterHandle(iceCls, method, iceRequest, iceRespose);
            }

            long spend = (System.currentTimeMillis() - begin);
            StatAnalyzer.getInstance().onResult(serviceId, methodId, begin, System.currentTimeMillis(),false);
            log.info("time:[{}.{}] to spend:{}ms", msgRequest.service, msgRequest.method, spend);
            log.info("josn: {}", JsonUtils.toJson(iceRespose));
            return iceRespose;//json;
        } catch (Throwable e) {
            e.printStackTrace();
            String msg = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
            //return JsonUtils.toJson(iceRespose);
            StatAnalyzer.getInstance().onResult(serviceId, methodId, begin, System.currentTimeMillis(),true);
            return returnRespose(303, msg);
        }
    }

    public String Invoke(MsgRequest msgRequest) {
        return JsonUtils.toJson(doInvoke(msgRequest));
    }

    public IceRespose doInvoke(IceRequest iceRequest) {
        return doInvoke(new MsgRequest(iceRequest.getService(), iceRequest.getMethod(), iceRequest.getExtData(),
                iceRequest.getAttr()));
    }

    public String Invoke(IceRequest iceRequest) {
        return JsonUtils.toJson(doInvoke(iceRequest));
    }
}
