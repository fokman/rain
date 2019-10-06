package com.rain.common.uitls;

import com.rain.common.ice.impl.IceMessageService;
import com.rain.common.servcie.StartupUtils;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Exception;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;
import lombok.extern.slf4j.Slf4j;

import static com.zeroc.Ice.Util.initialize;

@Slf4j
public class ServerStart extends com.zeroc.Ice.Application {

    public static void main(String[] args) {
        ServerStart serverStart = new ServerStart();
        int status = serverStart.main("ServerStart", args);
        System.exit(status);
    }


    @Override
    public int run(String[] args) {
        int status = 0;
        Communicator communicator = null;
        try {
            StartupUtils.init();
            // 初始化Communicator对象，args可以传一些初始化参数，如连接超时，初始化客户端连接池的数量等
            communicator = initialize(args);
            // 创建名为adapter的适配器，并要求适配器使用缺省的协议（TCP/IP 端口为20000的请求）
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("MessageServiceAdapter",
                    "tcp -p 20000");
            // 实例化一个message Object服务对象
            IceMessageService messageService = new IceMessageService();
            // 将服务单元增加到适配器中，并给服务对象指定ID为MessageService，该名称用于唯一确定一个服务单元
            adapter.add(messageService, Util.stringToIdentity("MessageService"));
            // 激活适配器
            adapter.activate();
            // 让服务在退出之前，一直持续对请求的监听
            communicator.waitForShutdown();
        } catch (Exception e) {
            log.error("start server error:{}", e);
            status = 1;
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
        }
        return status;
    }
}
