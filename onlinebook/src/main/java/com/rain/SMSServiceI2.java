package com.rain;

import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book.OnlineBook;
import com.hp.tel.ice.book.OnlineBookPrx;
import com.hp.tel.ice.message.SMSService;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;

public class SMSServiceI2 implements com.zeroc.IceBox.Service, SMSService {

    private ObjectAdapter adapter;

    public void sendSms(String msg, Current current) {
        if (msg.startsWith("book")) {
            try {
                com.zeroc.Ice.ObjectPrx base = adapter.getCommunicator().stringToProxy("OnlineBook@OnlineBookAdapter");
                OnlineBookPrx onlineBook = OnlineBookPrx.checkedCast(base);
                Message bookMsg = new Message();
                bookMsg.name = "Mr wang";
                bookMsg.type = 3;
                bookMsg.price = 99.99;
                bookMsg.content = "adbcad";
                onlineBook.bookTick(bookMsg);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void start(String name, Communicator communicator, String[] strings) {
        adapter = communicator.createObjectAdapter(name);
        adapter.add(this, com.zeroc.Ice.Util.stringToIdentity(name));
        adapter.activate();
        System.out.println("startd sms" + name);

    }

    public void stop() {
        System.out.println("stop sms .");
        adapter.deactivate();
    }
}
