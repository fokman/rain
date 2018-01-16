package com.rain.ice.utils;

import com.rain.ice.service.IceMessageService;

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
			IceMessageService object = new IceMessageService();
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
