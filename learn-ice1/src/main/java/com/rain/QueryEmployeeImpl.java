package com.rain;

import com.yujie.ice.info.EmployeeInfo;
import com.yujie.ice.info.QueryEmployee;

public class QueryEmployeeImpl implements QueryEmployee {

    @Override
    public EmployeeInfo query(EmployeeInfo msg, com.zeroc.Ice.Current current) {
        EmployeeInfo ei = new EmployeeInfo();
        ei.age = 23;
        ei.name = msg.name;
        ei.isLeave = false;
        ei.salary = 2000.0;
        ei.remark = "he is a good employee";
        return ei;
    }
}
