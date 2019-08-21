package com.rain.common.stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ServiceHigh {
    private static final int CAPACITY_SIZE = 5000;
    private static final int DELETE_SIZE = 10;
    private long slowTime = 3000;
    private LinkedHashMap<String, ServiceFrequency> serFrequencyMap = new LinkedHashMap<String, ServiceFrequency>();


    public void addMethod(String sname, long executeTime, long startTime, long endTime, boolean isError) {
        if (this.serFrequencyMap.size() >= CAPACITY_SIZE) {
            // 删除频率次数排名靠后的
            List<Map.Entry<String, ServiceFrequency>> list = this.sortFrequency(serFrequencyMap, true);
            for (int i = 0; i < DELETE_SIZE; i++) {

                Entry<String, ServiceFrequency> entry = list.get(i);
                String key = entry.getKey();
                this.serFrequencyMap.remove(key);
            }
        }

        ServiceFrequency frequency = this.serFrequencyMap.get(sname);
        if (frequency == null) {
            frequency = new ServiceFrequency();
            frequency.setName(sname);
        }
        frequency.setSlowTime(slowTime);
        frequency.setLastTime(endTime);
        frequency.incCount();
        if (isError) {
            frequency.incErrorCount();
        }
        frequency.setExecuteTime(executeTime);
        this.serFrequencyMap.put(sname, frequency);
    }

    public void setSlowTime(long time) {
        this.slowTime = time;
    }

    /**
     * 获取 SQL 访问频率
     */
    public List<Map.Entry<String, ServiceFrequency>> getSerFrequency(boolean isCount, boolean isClear) {
        List<Map.Entry<String, ServiceFrequency>> list = null;
        //      lock.readLock().lock();
        //      try {
        if (isCount) {
            list = this.sortFrequency(serFrequencyMap, false);
        } else {
            list = this.sortSlow(serFrequencyMap, false);
        }
//        } finally {
        //        lock.readLock().unlock();
        //      }
        if (isClear) {
            clearSqlFrequency();  // 获取 高频SQL后清理
        }

        return list;
    }

    public List<Map.Entry<String, ServiceFrequency>> getErrorFrequency(boolean isCount, boolean isClear) {
        List<Map.Entry<String, ServiceFrequency>> list = null;
        if (isCount) {
            list = this.sortFrequency(serFrequencyMap, false);
        } else {
            list = this.sortError(serFrequencyMap, false);
        }

        if (isClear) {
            clearSqlFrequency();  // 获取 后清理
        }
        return list;
    }

    private void clearSqlFrequency() {
        serFrequencyMap.clear();
    }

    /**
     * 排序
     */
    private List<Map.Entry<String, ServiceFrequency>> sortFrequency(HashMap<String, ServiceFrequency> map,
                                                                    final boolean bAsc) {

        List<Map.Entry<String, ServiceFrequency>> list = new ArrayList<Map.Entry<String, ServiceFrequency>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, ServiceFrequency>>() {
            public int compare(Map.Entry<String, ServiceFrequency> o1, Map.Entry<String, ServiceFrequency> o2) {

                if (!bAsc) {
                    return o2.getValue().getCount() - o1.getValue().getCount(); // 降序
                } else {
                    return o1.getValue().getCount() - o2.getValue().getCount(); // 升序
                }
            }
        });

        return list;

    }

    /**
     * 排序
     */
    private List<Map.Entry<String, ServiceFrequency>> sortSlow(HashMap<String, ServiceFrequency> map,
                                                               final boolean bAsc) {

        List<Map.Entry<String, ServiceFrequency>> list = new ArrayList<Map.Entry<String, ServiceFrequency>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, ServiceFrequency>>() {
            public int compare(Map.Entry<String, ServiceFrequency> o1, Map.Entry<String, ServiceFrequency> o2) {

                if (!bAsc) {
                    return o2.getValue().getSlowCount() - o1.getValue().getSlowCount(); // 降序
                } else {
                    return o1.getValue().getSlowCount() - o2.getValue().getSlowCount(); // 升序
                }
            }
        });

        return list;

    }

    /**
     * 排序
     */
    private List<Map.Entry<String, ServiceFrequency>> sortError(HashMap<String, ServiceFrequency> map,
                                                                final boolean bAsc) {

        List<Map.Entry<String, ServiceFrequency>> list = new ArrayList<Map.Entry<String, ServiceFrequency>>(map.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<String, ServiceFrequency>>() {
            public int compare(Map.Entry<String, ServiceFrequency> o1, Map.Entry<String, ServiceFrequency> o2) {

                if (!bAsc) {
                    return o2.getValue().getErrorCount() - o1.getValue().getErrorCount(); // 降序
                } else {
                    return o1.getValue().getErrorCount() - o2.getValue().getErrorCount(); // 升序
                }
            }
        });

        return list;

    }
}
