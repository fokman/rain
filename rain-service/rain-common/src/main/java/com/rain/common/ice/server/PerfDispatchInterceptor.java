package com.rain.common.ice.server;

import com.zeroc.Ice.Object;
import com.zeroc.Ice.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class PerfDispatchInterceptor extends DispatchInterceptor {

    private static final Map<Identity, Object> id2ObjectMAP = new ConcurrentHashMap<>();
    private static final PerfDispatchInterceptor self = new PerfDispatchInterceptor();

    /**
     *
     */

    public static PerfDispatchInterceptor getINSTANCE() {
        return self;
    }

    public  DispatchInterceptor addICEObject(Identity id,
                                                   com.zeroc.Ice.Object iceObj) {
        id2ObjectMAP.put(id, iceObj);
        return self;
    }

    /**
     * 此方法可以做任何拦截，类似AOP.
     */
    @Override
    public CompletionStage dispatch(Request request) {
        Identity theId = request.getCurrent().id;
        String information = "dispach req,method:" + request.getCurrent().operation
                + " service:" + theId.name + " server address:"
                + request.getCurrent().con;

        log.info(" {} begin", information);

        try {

            CompletionStage reslt = id2ObjectMAP.get(request.getCurrent().id)
                    .ice_dispatch(request);
            log.info(" {} success", information);
            return reslt;
        } catch (RuntimeException e) {
            log.info(" error ：{}", e);
            throw e;

        } catch (UserException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void removeICEObject(Identity id) {
        log.info("remove ice object " + id);
        id2ObjectMAP.remove(id);

    }

}
