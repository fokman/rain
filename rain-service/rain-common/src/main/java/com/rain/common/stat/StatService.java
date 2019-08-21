package com.rain.common.stat;

import com.alibaba.druid.stat.DruidStatManagerFacade;

import com.rain.common.ice.model.IceRequest;
import com.rain.common.ice.model.IceRespose;
import com.rain.common.servcie.config.Service;

@Service(name = "StatService")
public class StatService {
    private static DruidStatManagerFacade statManagerFacade = DruidStatManagerFacade.getInstance();

    public IceRespose basic(IceRequest context) {
        IceRespose iceRespose = new IceRespose();
        iceRespose.setCode(0);
        iceRespose.setData(statManagerFacade.returnJSONBasicStat());
        return iceRespose;
    }

    public IceRespose datasource(IceRequest context) {
        IceRespose iceRespose = new IceRespose();
        iceRespose.setCode(0);
        iceRespose.setData(statManagerFacade.getDataSourceStatDataList());
        return iceRespose;
    }


    public IceRespose activeConnection(IceRequest context) {
        IceRespose iceRespose = new IceRespose();
        iceRespose.setCode(0);
        iceRespose.setData(statManagerFacade.getActiveConnStackTraceList());
        return iceRespose;
    }

    public IceRespose sql(IceRequest context) {
        IceRespose iceRespose = new IceRespose();
        iceRespose.setCode(0);
        iceRespose.setData(statManagerFacade.getSqlStatDataList(null));
        return iceRespose;
    }

    public IceRespose wall(IceRequest context) {
        IceRespose iceRespose = new IceRespose();
        iceRespose.setCode(0);
        iceRespose.setData(statManagerFacade.getWallStatMap(null));
        return iceRespose;
    }

    //统计服务方法调用次数，按次数排序
    public IceRespose count(IceRequest context) {
        IceRespose iceRespose = new IceRespose();
        iceRespose.setCode(0);
        iceRespose.setData(StatAnalyzer.getInstance().getStat(false));
        return iceRespose;
    }

    //统计服务方法调用次数，按慢查询次数排序
    public IceRespose slow(IceRequest context) {
        IceRespose iceRespose = new IceRespose();
        iceRespose.setCode(0);
        iceRespose.setData(StatAnalyzer.getInstance().getStat(true));
        return iceRespose;
    }

    //统计服务方法调用次数，按错误次数排序
    public IceRespose error(IceRequest context) {
        IceRespose iceRespose = new IceRespose();
        iceRespose.setCode(0);
        iceRespose.setData(StatAnalyzer.getInstance().getErrorStat());
        return iceRespose;
    }
}
