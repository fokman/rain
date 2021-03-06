package com.rain.common.uitls;

import com.rain.common.ice.v1.impl.IceMessageService;
import com.rain.common.servcie.StartupUtils;


public class TestServerUtils {

    public static void statServer(String[] args) {
        int status = 0;
        Ice.Communicator ic = null;
        try {
            StartupUtils.init();
            // 初始化Communicator对象，args可以传一些初始化参数，如连接超时，初始化客户端连接池的数量等
            ic = Ice.Util.initialize(args);
            // 创建名为OnlineBookAdapter的适配器，并要求适配器使用缺省的协议（TCP/IP 端口为10000的请求）
            Ice.ObjectAdapter adapter = ic.createObjectAdapterWithEndpoints("MessageServiceAdapter",
                    "default -p 20000");
            // 实例化一个OnlineBook服务对象
            IceMessageService object = new IceMessageService();
            // 将服务单元增加到适配器中，并给服务对象指定ID为OnlineBook，该名称用于唯一确定一个服务单元
            adapter.add(object, Ice.Util.stringToIdentity("MessageService"));
            // 激活适配器
            adapter.activate();
            // 让服务在退出之前，一直持续对请求的监听
            System.out.print("server started ");
            ic.waitForShutdown();

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
