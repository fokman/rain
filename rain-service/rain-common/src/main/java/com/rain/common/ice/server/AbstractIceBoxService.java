package com.rain.common.ice.server;


import com.zeroc.Ice.*;
import com.zeroc.IceBox.Service;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

@Slf4j
public abstract class AbstractIceBoxService implements Service {
    protected ObjectAdapter _adapter;
    protected Identity id;
    protected static Logger iceLogger = new Sl4jLogerI("communicator");

    public void start(String name, Communicator communicator, String[] args) {
        com.zeroc.Ice.Util.setProcessLogger(iceLogger);
        // 创建objectAdapter，这里和service同名
        _adapter = communicator.createObjectAdapter(name);
        com.zeroc.Ice.Object object = this.createMyIceServiceObj(args);
        id = communicator.stringToIdentity(name);
        // _adapter.add(object, communicator.stringToIdentity(name));
        DispatchInterceptor interceptor = PerfDispatchInterceptor.addICEObject(id, object);

        _adapter.add(interceptor, id);

        addMyIceServiceObjFacets(_adapter, id);

        _adapter.activate();
        log.info(name + "{} service started ,with param size {}  detail:{},Arrays.toString(args)", name, args.length);
    }

    protected abstract void addMyIceServiceObjFacets(ObjectAdapter adapter, Identity id);

    /**
     * 创建具体的ICE 服务实例对象
     *
     * @param args 服务的配置参数，来自icegrid.xml文件
     * @return Ice.Object
     */
    public abstract com.zeroc.Ice.Object createMyIceServiceObj(String[] args);

    public void stop() {
        log.info("stopping service " + id + " ....");
        _adapter.destroy();
        PerfDispatchInterceptor.removeICEObject(id);
        log.info("stopped service " + id + " stoped");

    }

}
