package com.rain.common.cache;

import lombok.Data;

/**
 * @author kunliu
 */
@Data
public class CacheStatic {

    private long maxSize;
    private long memorySize;
    private long itemSize;
    private long accessTimes;
    private long putTimes;
    private long hitTimes;
    private long lastAccesTime;
    private long lastPutTime;

    public void incAccessTimes() {
        this.accessTimes++;
        this.lastAccesTime = System.currentTimeMillis();
    }

    public void incHitTimes() {
        this.hitTimes++;
        this.accessTimes++;
        this.lastAccesTime = System.currentTimeMillis();
    }

    public void incPutTimes() {
        this.putTimes++;
        this.lastPutTime = System.currentTimeMillis();
    }


    public void reset() {
        this.accessTimes = 0;
        this.hitTimes = 0;
        this.itemSize = 0;
        this.lastAccesTime = 0;
        this.lastPutTime = 0;
        this.memorySize = 0;
        this.putTimes = 0;

    }

    @Override
    public String toString() {
        return "CacheStatic [memorySize=" + memorySize + ", itemSize="
                + itemSize + ", accessTimes=" + accessTimes + ", putTimes="
                + putTimes + ", hitTimes=" + hitTimes + ", lastAccesTime="
                + lastAccesTime + ", lastPutTime=" + lastPutTime + "]";
    }

}
