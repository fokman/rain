package com.rain.ice.utils;

import Ice.Communicator;
import com.rain.ice.message.MessageServicePrx;
import com.rain.ice.message.MessageServicePrxHelper;
import com.rain.ice.message.MsgRequest;
import com.rain.ice.model.IceRequest;
import com.rain.ice.model.IceResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static Ice.Util.initialize;
import static java.lang.System.exit;

@Slf4j
public class TestClientUtils {

    private static final String MSG_SERVICE = "MessageService:default -p 20000";

    public static IceResponse doService(IceRequest iceRequest) {
        return doService(iceRequest, null);
    }


    public static IceResponse doService(IceRequest iceRequest, String[] args) {
        int status = 0;
        log.info("service starting ------>");
        Communicator ic = null;
        try {
            ic = initialize(args);
            Ice.ObjectPrx base = ic.stringToProxy(MSG_SERVICE);
            MessageServicePrx messageServicePrx = MessageServicePrxHelper.checkedCast(base);
            if (messageServicePrx == null) {
                throw new Error("Invalid proxy");
            }
            MsgRequest msgRequest = new MsgRequest(iceRequest.getService(), iceRequest.getMethod(), iceRequest.getExtraData(),
                    iceRequest.getAttr());
            String str = messageServicePrx.doInvoke(msgRequest);
            IceResponse rs = JsonUtils.toObject(str, IceResponse.class);
            return rs;
        } catch (Exception e) {
            log.info("{}", e);
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
