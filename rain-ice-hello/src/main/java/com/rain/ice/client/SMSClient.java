package com.rain.ice.client;

import com.rain.ice.message.SMSServicePrx;
import com.rain.ice.message.SMSServicePrxHelper;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/10
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class SMSClient {
    public static void main(String[] args) {
        int status = 0;
        Ice.Communicator ic = null;
        try {
            ic = Ice.Util.initialize(args);
            Ice.ObjectPrx base = ic
                    .stringToProxy("SMSService:default -p 10001");
            SMSServicePrx smsSrvPrx =
                    SMSServicePrxHelper.checkedCast(base);
            if (smsSrvPrx == null) {
                throw new Error("Invalid proxy");
            }
//            long start = System.currentTimeMillis();
//            int count = 100000;
            smsSrvPrx.sendSMS("book msg to you ");
//            long used = System.currentTimeMillis() - start;
//            System.out.print("tps " + count * 1000.0 / used);
        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
        } finally {
            if (ic != null) {
                ic.destroy();
            }
        }
        System.exit(status);
    }
}
