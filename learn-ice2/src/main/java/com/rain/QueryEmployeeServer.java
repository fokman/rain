package com.rain;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ObjectAdapter;

public class QueryEmployeeServer implements com.zeroc.IceBox.Service {

    //创建适配器实例
    private ObjectAdapter adapter;

    public void start(String s, Communicator communicator, String[] strings) {
        adapter = communicator.createObjectAdapter(s);
        QueryEmployeeImpl servant = new QueryEmployeeImpl();
        adapter.add(servant, com.zeroc.Ice.Util.stringToIdentity(s));
        // 激活adapter
        adapter.activate();
        System.out.println("adapter has been activate");

    }

    public void stop() {
        adapter.destroy();
        System.out.println("adapter has been destory");
    }
}
