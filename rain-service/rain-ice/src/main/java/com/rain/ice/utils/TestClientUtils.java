package com.rain.ice.utils;

import com.rain.ice.message.MessageServicePrx;
import com.rain.ice.message.MessageServicePrxHelper;
import com.rain.ice.message.MsgRequest;
import com.rain.ice.model.IceRequest;
import com.rain.ice.model.IceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.System.exit;

public class TestClientUtils {

    private static Logger logger = LoggerFactory.getLogger(TestClientUtils.class);

    public static IceResponse doService(IceRequest iceRequest) {
        return doService(iceRequest, null);
    }

    private static final String MSG_SERVICE = "MessageService:default -p 20000";

    public static IceResponse doService(IceRequest iceRequest, String[] args) {
        int status = 0;
        logger.info("service starting ------>");
        Ice.Communicator ic = null;
        try {
            ic = Ice.Util.initialize(args);
            Ice.ObjectPrx base = ic.stringToProxy(MSG_SERVICE);
            MessageServicePrx messageServicePrx = MessageServicePrxHelper.checkedCast(base);
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
