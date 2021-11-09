package com.rain.common.uitls;

import cn.hutool.json.JSONUtil;
import com.rain.common.ice.v1.message.MessageServicePrx;
import com.rain.common.ice.v1.message.MsgRequest;
import com.rain.common.ice.v1.model.IceRequest;
import com.rain.common.ice.v1.model.IceRespose;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestClientUtils {

    public static IceRespose doService(IceRequest iceRequest) {
        return doService(iceRequest, null);
    }

    public static IceRespose doService(IceRequest iceRequest, String[] args) {
        int status = 0;
        Communicator ic = null;
        try {
            // 初始化通信器
            ic = Util.initialize(args);
            // 传入远程服务单元的名称、网络协议、IP以及端口，获取OnlineBook的远程代理，这里使用stringToProxy方式
            ObjectPrx base = ic.stringToProxy("MessageService:default -p 20000");
            // 通过checkedCast向下转型，获取OnlineBook接口的远程，并同时检测根据传入的名称获取服务单元是否OnlineBook的代理接口，如果不是则返回null对象
            MessageServicePrx messageServicePrx = MessageServicePrx.checkedCast(base);
            if (messageServicePrx == null) {
                throw new Error("Invalid proxy");
            }
            // 调用服务方法
            long start = System.currentTimeMillis();
            int count = 100;
            MsgRequest in = new MsgRequest(iceRequest.getService(), iceRequest.getMethod(), iceRequest.getExtData(),
                    iceRequest.getAttr());
            String str = messageServicePrx.doInvoke(in);
            IceRespose rs = JSONUtil.toBean(str, IceRespose.class);

            long used = System.currentTimeMillis() - start;
            System.out.println("tps " + count * 1000.0 / used);
            return rs;
        } catch (Exception e) {
            log.error("{}", e);
            status = 1;
            System.exit(status);
        } finally {
            if (ic != null) {
                ic.destroy();
            }
        }
        return null;

    }
}
