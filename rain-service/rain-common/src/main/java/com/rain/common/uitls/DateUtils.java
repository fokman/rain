package com.rain.common.uitls;

import java.text.DateFormat;
import java.util.Date;

public class DateUtils {
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static Date getDate() {
        return new Date(System.currentTimeMillis());
    }
    public static String getStrCurrtTime() {
        return formatTime(getDate());
    }

    public static String time(long time) {
        if (time == 0) {
            return "0 毫秒";
        }
        int d = (int) ((time / 86400000) % 86400000);
        int m = (int) ((time / 60000) % 60);
        int s = (int) ((time / 1000) % 60);
        int ms = (int) (time % 1000);
        String dd = (d > 0 ? d + "天" : "");
        String mm = (m > 0 ? m + "分" : "");
        String ss = (s > 0 ? s + "秒" : "");
        String mss = (ms > 0 ? ms + "毫秒" : "");
        return dd + mm + ss + mss;
    }

    public static String formatTime(Object time) {
        if (time != null) {
            return formatDateTime(time, YYYY_MM_DD_HH_MM_SS);
        }
        return "";
    }

    public static String formatDateTime(Object dateTime, String pattern) {
        if (dateTime != null) {
            DateFormat dateFormat = new java.text.SimpleDateFormat(pattern);
            return dateFormat.format(dateTime);
        }
        return "";
    }

}
