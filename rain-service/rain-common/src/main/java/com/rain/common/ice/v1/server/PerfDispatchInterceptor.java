package com.rain.common.ice.v1.server;

import com.zeroc.Ice.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletionStage;

@Slf4j
public class PerfDispatchInterceptor extends DispatchInterceptor {

    private static final long serialVersionUID = 1L;
    private static final Map<Identity, com.zeroc.Ice.Object> id2ObjectMAP = new java.util.concurrent.ConcurrentHashMap<>();
    private static final PerfDispatchInterceptor self = new PerfDispatchInterceptor();

    public static PerfDispatchInterceptor getINSTANCE() {
        return self;
    }

    public static DispatchInterceptor addICEObject(Identity id,
                                                   com.zeroc.Ice.Object iceObj) {
        id2ObjectMAP.put(id, iceObj);
        return self;
    }

    /**
     * 此方法可以做任何拦截，类似AOP.
     */
    @Override
    public CompletionStage<OutputStream> dispatch(Request request) {
        Identity theId = request.getCurrent().id;
        // request.getCurrent().con会打印出 local address = 16.156.210.172:50907
        // remote address = 16.156.210.172:51147 这样的信
        // 其中 local address 为被访问的服务的地址端口，Remote Address为客户端的端口
        String inf = "dispach req,method:" + request.getCurrent().operation
                + " service:" + theId.name + " server address:"
                + request.getCurrent().con;

        log.info("{} begin", inf);

        try {

            CompletionStage<OutputStream> reslt = id2ObjectMAP.get(request.getCurrent().id).ice_dispatch(request);
            log.info("{} success", inf);
            return reslt;
        } catch (RuntimeException | UserException e) {
            log.info("{} error {}", inf, e);
        }
        return null;
    }

    public static void removeICEObject(Identity id) {
        log.info("remove ice object {}", id);
        id2ObjectMAP.remove(id);

    }

}
