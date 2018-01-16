package com.rain.ice.utils;

import com.rain.common.utils.JsonUtils;
import com.rain.ice.message.MessageServicePrx;
import com.rain.ice.message.MessageServicePrxHelper;
import com.rain.ice.message.MsgRequest;
import com.rain.ice.mode.IceRequest;
import com.rain.ice.mode.IceResponse;

public class TestClientUtils {

    public static IceResponse doService(IceRequest iceRequest) {
        return doService(iceRequest, null);
    }

    public static IceResponse doService(IceRequest iceRequest, String[] args) {
        int status = 0;
        Ice.Communicator ic = null;
        try {
            ic = Ice.Util.initialize(args);
            Ice.ObjectPrx base = ic.stringToProxy("MessageService:default -p 20000");
            MessageServicePrx messageServicePrx = MessageServicePrxHelper.checkedCast(base);
            if (messageServicePrx == null) {
                throw new Error("Invalid proxy");
            }
            // 调用服务方法
            long start = System.currentTimeMillis();
            int count = 100;
            MsgRequest in = new MsgRequest(iceRequest.getService(), iceRequest.getMethod(), iceRequest.getExtraData(),
                    iceRequest.getAttr());

            String str = messageServicePrx.doInvoke(in);
            IceResponse rs = JsonUtils.toObject(str, IceResponse.class);

            long used = System.currentTimeMillis() - start;
            System.out.println("tps " + count * 1000.0 / used);
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
