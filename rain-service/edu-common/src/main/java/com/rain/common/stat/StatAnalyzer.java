package com.rain.common.stat;

import java.util.List;
import java.util.Map;

public class StatAnalyzer {
    //慢调用的时间阀值
    private long SLOW_TIME = 3000;
    private ServiceHigh sHighStat = new ServiceHigh();//服务方法统计
    private final static StatAnalyzer instance = new StatAnalyzer();

    private StatAnalyzer() {
    }

    public static StatAnalyzer getInstance() {
        return instance;
    }

    public void setSlowTime(long time) {
        this.SLOW_TIME = time;
    }

    public void onResult(String service, String method, long beginTime, long endTime, boolean isError) {
        sHighStat.setSlowTime(SLOW_TIME);
        sHighStat.addMethod(service + "." + method, endTime - beginTime, beginTime, endTime, isError);
    }

    public void onResult(String sysName, String service, String method, long beginTime, long endTime, boolean isError) {
        sHighStat.setSlowTime(SLOW_TIME);
        sHighStat.addMethod(service + "." + method, endTime - beginTime, beginTime, endTime, isError);
        //sysStat.setSlowTime(SLOW_TIME);
        //sysStat.addMethod(sysName, endTime-beginTime, beginTime, endTime,isError);
    }

    public List<Map.Entry<String, ServiceFrequency>> getStat(boolean isSlow) {
        if (isSlow) {
            return sHighStat.getSerFrequency(false, false);
        } else {
            return sHighStat.getSerFrequency(true, false);
        }
    }

    public List<Map.Entry<String, ServiceFrequency>> getErrorStat() {
        return sHighStat.getErrorFrequency(false, false);
    }
}
