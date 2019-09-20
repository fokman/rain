package com.rain.common.stat;

import lombok.Data;

import java.io.Serializable;

@Data
public class ServiceFrequency implements Serializable {
    private String name;
    private int count = 0;
    private int slowCount = 0;
    private int errorCount = 0;
    private long lastTime = 0;
    private long executeTime = 0;
    private long allExecuteTime = 0;
    private long maxTime = 0;
    private long avgTime = 0;
    private long minTime = 0;
    private long slowTime = 3000;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSlowTime(long time) {
        this.slowTime = time;
    }

    public int getCount() {
        return count;
    }

    public int getSlowCount() {
        return slowCount;
    }

    public void incCount() {
        this.count++;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void incErrorCount() {
        this.errorCount++;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public long getMaxTime() {
        return maxTime;
    }

    public long getMinTime() {
        return minTime;
    }

    public long getAvgTime() {
        return avgTime;
    }

    public void setExecuteTime(long execTime) {
        if (execTime > this.maxTime) {
            this.maxTime = execTime;
        }
        if (this.minTime == 0) {
            this.minTime = execTime;
        }
        if (execTime > 0) {
            if (execTime < this.minTime) {
                this.minTime = execTime;
            }
        }
        this.allExecuteTime += execTime;
        if (count > 0) {
            this.avgTime = this.allExecuteTime / count;
        }
        this.executeTime = execTime;
        if (execTime > slowTime) {
            this.slowCount++;
        }
    }
}

