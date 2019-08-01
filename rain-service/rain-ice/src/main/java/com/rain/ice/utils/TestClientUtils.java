package com.rain.ice.utils;

import com.rain.ice.message.MessageServicePrx;
import com.rain.ice.message.MsgRequest;
import com.rain.ice.model.IceRequest;
import com.rain.ice.model.IceResponse;
import lombok.extern.slf4j.Slf4j;

import static java.lang.System.exit;

@Slf4j
public class TestClientUtils {


    public static IceResponse doService(IceRequest iceRequest) {
        return doService(iceRequest, null);
    }


    public static IceResponse doService(IceRequest iceRequest, String[] args) {
        int status = 0;
        com.zeroc.Ice.Communicator ic = null;
        try {
            ic = com.zeroc.Ice.Util.initialize(args);
            com.zeroc.Ice.ObjectPrx base = ic.stringToProxy("MessageService:default -p 20000");
            MessageServicePrx messageServicePrx = MessageServicePrx.checkedCast(base);
            if (messageServicePrx == null) {
                throw new Error("Invalid proxy");
            }
            MsgRequest in = new MsgRequest(iceRequest.getService(), iceRequest.getMethod(), iceRequest.getExtraData(),
                    iceRequest.getAttr());
            String str = messageServicePrx.doInvoke(in);
            IceResponse rs = JsonUtils.toObject(str, IceResponse.class);
            return rs;
        } catch (Exception e) {
            status = 1;
            exit(status);

        } finally {
            if (ic != null) {
                ic.destroy();
            }
        }
        return null;
    }
}
