package com.rain.ice.server;

import Ice.Communicator;
import Ice.ObjectAdapter;
import Ice.Util;
import com.rain.ice.service.OnlineBookI;

import static Ice.Util.initialize;

/**
 * Project Name: Rain
 * <p>
 * Create Date: 2018/1/10
 * <p>
 * Copyright(c) 2018 Virtue Intelligent Network Ltd, co. All Rights Reserved.
 */
public class Server {
    public static void main(String[] args) {
        int status =0;
        Communicator communicator = null;

        try {
            communicator = initialize(args);
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("OnlineBookAdapter","default -p 10000");
            OnlineBookI object = new OnlineBookI();
            adapter.add(object, Util.stringToIdentity("OnlineBook"));
            adapter.activate();
            System.out.println("server started");
            communicator.waitForShutdown();
        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
        } finally {
            if (communicator!=null){
                communicator.destroy();
            }
        }
        System.exit(status);
    }

}
