package com.rain.ice;


import com.rain.ice.service.IceMessageService;
import com.rain.ice.utils.InitRegisterService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MainServer {

    public static void main(String[] args) {
        startServer(args);
    }

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
            log.info("Ice Server started.");
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