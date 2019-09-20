package com.rain;

import com.yujie.ice.info.EmployeeInfo;
import com.yujie.ice.info.QueryEmployeePrx;

public class QueryEmployeeClient {
    public static void main(String[] args) {
        com.zeroc.Ice.Communicator communicator = null;
        try {
            //初始化ice通信器communicator,可以使用args传入一下ice初始化的参数如超时时间，线程池大小等
            communicator = com.zeroc.Ice.Util.initialize(args);
            // 传入远程服务单元的 ice对象标识符  协议默认tcp 主机 已经服务监听端口
            com.zeroc.Ice.ObjectPrx objectPrx = communicator.stringToProxy("queryServer:default -h localhost -p 10006");
            // 检查通用客户端代理op 是不是queryServer对象标识符所关联的ice对象的代理
            QueryEmployeePrx qp = QueryEmployeePrx.checkedCast(objectPrx);
            if (qp == null) {
                throw new Exception("qp == null");
            }
            // 构造传入参数
            EmployeeInfo ei = new EmployeeInfo();
            ei.name = "yujie.wang";
            // 调用接口
            EmployeeInfo result = qp.query(ei);
            if (result == null) {
                throw new Exception("result == null");
            }
            // 输出服务端返回结果
            System.out.println(result.remark);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }
}
