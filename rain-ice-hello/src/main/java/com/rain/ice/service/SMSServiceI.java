package com.rain.ice.service;

import Ice.Communicator;
import Ice.Current;
import Ice.Logger;
import Ice.ObjectAdapter;
import IceBox.Service;
import com.rain.ice.book.Message;
import com.rain.ice.book.OnlineBookPrx;
import com.rain.ice.book.OnlineBookPrxHelper;
import com.rain.ice.message._SMSServiceDisp;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/10
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class SMSServiceI extends _SMSServiceDisp implements Service {
    private Logger logger;
    private ObjectAdapter objectAdapter;

    @Override
    public void start(String name, Communicator communicator, String[] strings) {
        this.logger = communicator.getLogger().cloneWithPrefix(name);
        objectAdapter = communicator.createObjectAdapter(name);
        Ice.Object object = this;
        logger.print("identiy name is "+name);
        objectAdapter.add(object,communicator.stringToIdentity(name));
        objectAdapter.activate();
        logger.trace("control","started");
    }

    @Override
    public void stop() {
        logger.trace("control","stoped");
        objectAdapter.destroy();
    }

    @Override
    public void sendSMS(String msg, Current __current) {
        logger.trace("service","send sms message :"+msg);
        if (msg.startsWith("book")){
            Ice.ObjectPrx base;
            try {
                base = objectAdapter.getCommunicator().stringToProxy("OnlineBook");
                OnlineBookPrx onlineBook = OnlineBookPrxHelper.checkedCast(base);
                logger.trace("service","find proxy "+onlineBook);
                Message bookMsg = new Message();
                bookMsg.name = "Liu ken";
                bookMsg.type = 3;
                bookMsg.price = 99.98;
                bookMsg.valid = true;
                bookMsg.content = "Zeroc ice学习笔记";
                onlineBook.bookTick(bookMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
