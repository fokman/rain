package com.rain.ice.utils;

import com.rain.ice.service.IceMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Service
public class StartServer {
    private static final Logger logger = LoggerFactory.getLogger(StartServer.class);

    public static void startServer(String... args) {
        int status = 0;
        Ice.Communicator ic = null;
        try {
            InitRegisterService.init();
            ic = Ice.Util.initialize(args);
            Ice.ObjectAdapter adapter = ic.createObjectAdapterWithEndpoints("MessageServiceAdapter",
                    "default -p 20000");
            IceMessageService object = new IceMessageService();
            adapter.add(object, Ice.Util.stringToIdentity("MessageService"));
            adapter.activate();
            logger.info("Ice server started.");
            ic.waitForShutdown();
        } catch (Exception e) {
            e.printStackTrace();
            status = 1;
            System.exit(status);
        } finally {
            if (ic != null) {
                ic.destroy();
            }
        }
        System.exit(status);
    }
}
