package com.rain.common.uitls;

import javax.sql.DataSource;

public class ContextUtils {

    private static boolean bdataSource = true;
    private static DataSource dataSource;
    private static boolean bIdWorker = true;
    private static IdWorker idWorker;

    public static void setDataSource(DataSource dataSource) {
        if (bdataSource) {
            ContextUtils.dataSource = dataSource;
            bdataSource = false;
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    public static void setIdWorker(IdWorker idWorker) {
        if (bIdWorker) {
            ContextUtils.idWorker = idWorker;
            bIdWorker = false;
        }
    }

    public static long nextId() {
        return idWorker.nextId();
    }

    public static String nextIdStr() {
        return String.valueOf(idWorker.nextId());
    }

}
