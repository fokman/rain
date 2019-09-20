package com.rain;

public class QueryEmployeeServer {
    public static void main(String[] args) {
        int state = 0;
        com.zeroc.Ice.Communicator communicator = null;
        try {
            //初始化ice通信器communicator,可以使用args传入一下ice初始化的参数如超时时间，线程池大小等
            communicator = com.zeroc.Ice.Util.initialize(args);
            //创建一个名为queryEmployeeAdapter的适配器并且默认使用tcp协议  服务部署在10.4.30.81机器上 服务开启10006监听端口
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("queryEmployeeAdapter", "default -p 10006");
            // 创建服务端代码实现servant
            QueryEmployeeImpl servant = new QueryEmployeeImpl();
            // 将servant与ice对象标识符建立映射关系，并添加到ice对象适配器中
            adapter.add(servant, com.zeroc.Ice.Util.stringToIdentity("queryServer"));
            // 激活对象适配器
            adapter.activate();
            System.out.println("QueryEmployeeServer adapter activate");
            // 服务在退出之前一直保持监听状态
            communicator.waitForShutdown();
        } catch (Exception e) {
            state = 1;
            System.out.println(e);
        } finally {
            if (communicator != null) {
                communicator.destroy();
            }
        }
        System.out.println("state: " + state);
    }
}
