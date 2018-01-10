package com.rain.ice.client;

import Ice.Communicator;
import com.rain.ice.book.Message;
import com.rain.ice.book.OnlineBookPrx;
import com.rain.ice.book.OnlineBookPrxHelper;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/10
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class Client {

    public static void main(String[] args) {
        int status = 0;
        Communicator communicator = null;
        try {
            communicator = Ice.Util.initialize(args);
            Ice.ObjectPrx base = communicator.stringToProxy("OnlineBook:default -p 10000");
            OnlineBookPrx onlineBookPrx = OnlineBookPrxHelper.checkedCast(base);
            if (onlineBookPrx == null) {
                throw new Error("Invalid proxy");
            }

            Message msg = new Message();
            msg.name = "Mr wang";
            msg.type = 3;
            msg.price = 99.99;
            msg.valid = true;
            msg.content = "学习手册";
            long start = System.currentTimeMillis();
            int count = 2;
            for (int i = 0; i < count; i++){
                onlineBookPrx.bookTick(msg);
            }
            long used = System.currentTimeMillis()-start;

        } catch (Error error) {
            error.printStackTrace();
            status = 1;
        } finally {
            if (communicator!=null){
                communicator.destroy();
            }
        }
        System.exit(status);
    }
}
