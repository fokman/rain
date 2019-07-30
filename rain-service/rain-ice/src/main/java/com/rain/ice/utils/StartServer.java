package com.rain.ice.utils;

import com.rain.ice.service.IceMessageService;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartServer {

    public static void startServer(String... args) {
        int status = 0;
        Communicator communicator = null;
        try {
            InitRegisterService.init();
            communicator = Util.initialize(args);
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("MessageServiceAdapter",
                    "default -p 20000");
            IceMessageService object = new IceMessageService();
            adapter.add(object, Util.stringToIdentity("MessageService"));
            adapter.activate();
            log.info("Ice server started.");
            communicator.waitForShutdown();
        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
            System.exit(status);
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
        }
        System.exit(status);
    }
}
