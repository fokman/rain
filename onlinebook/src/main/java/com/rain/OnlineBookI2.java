package com.rain;

import com.hp.tel.ice.book.Message;
import com.hp.tel.ice.book.OnlineBook;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;

public class OnlineBookI2 implements com.zeroc.IceBox.Service, OnlineBook {

    private ObjectAdapter adapter;


    @Override
    public void start(String name, Communicator communicator, String[] strings) {
        adapter = communicator.createObjectAdapter(name);
//        com.zeroc.Ice.Object object = this;
        adapter.add(this, com.zeroc.Ice.Util.stringToIdentity(name));
        adapter.activate();
        System.out.println("startd " + name);
    }

    @Override
    public void stop() {
        System.out.println("stoped ");
        adapter.deactivate();
    }

    @Override
    public Message bookTick(Message msg, Current current) {
        System.out.println("book tick to call: " + msg.content);
        
        return msg;
    }
}
