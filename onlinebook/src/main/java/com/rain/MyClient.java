package com.rain;

import com.hp.tel.ice.message.SMSServicePrx;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

public class MyClient {

    public static void main(String[] args) {
        int status = 0;
        Communicator communicator = null;
        String[] initParams = new String[]{"--Ice.Default.Locator=IceGrid/Locator: tcp -h localhost -p 4061"};
        try {
            communicator = Util.initialize(initParams);

            ObjectPrx base = communicator.stringToProxy("SMSService@SMSServiceAdapter");
            SMSServicePrx smsServicePrx = SMSServicePrx.checkedCast(base);
//            smsServicePrx.
            if (smsServicePrx == null)
                throw new Error("Invalid proxy");

            smsServicePrx.sendSms("book");
        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
        }
        System.exit(status);
    }

}
