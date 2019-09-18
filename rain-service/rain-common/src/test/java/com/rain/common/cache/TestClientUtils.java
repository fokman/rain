package com.rain.common.cache;

import com.rain.common.ice.message.MessageServicePrx;
import com.rain.common.ice.message.MsgRequest;
import com.rain.common.ice.model.IceRequest;
import com.rain.common.ice.model.IceRespose;
import com.rain.common.uitls.JsonUtils;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;


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
            ObjectPrx base = ic.stringToProxy("MessageService:default -p 20000");
            MessageServicePrx messageServicePrx = MessageServicePrx.checkedCast(base);
            if (messageServicePrx == null) {
                throw new Error("Invalid proxy");
            }
            // 调用服务方法
            long start = System.currentTimeMillis();
            int count = 100;
            MsgRequest in = new MsgRequest(iceRequest.getService(), iceRequest.getMethod(), iceRequest.getExtraData(),
                    iceRequest.getAttr());
            String str = messageServicePrx.doInvoke(in);
            IceRespose rs = JsonUtils.toObject(str, IceRespose.class);

            long used = System.currentTimeMillis() - start;
            System.out.println("tps " + count * 1000.0 / used+"");
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
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
