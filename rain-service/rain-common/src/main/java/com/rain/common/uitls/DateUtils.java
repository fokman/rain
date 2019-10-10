package com.rain.common.uitls;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_12HH_MM_SS = "yyyy-MM-dd hh:mm:ss";
    public static final String YYYY_MM_DD_12HH_MM_SS_SSS = "yyyy-MM-dd hh:mm:ss";
    public static Date getDate() {
        return new Date(System.currentTimeMillis());
    }

    public static String getStrCurrtDate() {
        return formatDate(getDate());
    }

    public static String getStrCurrtTime() {
        return formatTime(getDate());
    }

    // ---------------------------------------------------------------------------------------
    public static String getStrCurrtYear() {
        return getStrYear(getDate());
    }

    public static String getStrCurrtMonth() {
        return getStrMouth(getDate());
    }

    public static String getStrCurrtDay() {
        return getStrDay(getDate());
    }

    // ---------------------------------------------------------------------------------------
    public static int getIntCurrtDate() {
        return getIntDate(getDate());
    }

    public static int getIntCurrtYear() {
        return getIntYear(getDate());
    }

    public static int getIntCurrtMonth() {
        return getIntMouth(getDate());
    }

    public static int getIntCurrtDay() {
        return getIntDay(getDate());
    }

    public static int getIntCurrtMonthStart() {
        return getIntDate(getMonthStart(getDate()));
    }

    public static int getIntCurrtMonthEnd() {
        return getIntDate(getMonthEnd(getDate()));
    }

    public static int getIntCurrtQuarterStart() {
        return getIntDate(getQuarterStart(getDate()));
    }

    public static int getIntCurrtQuarterEnd() {
        return getIntDate(getQuarterEnd(getDate()));
    }

    public static int getIntCurrtYearStart() {
        return getIntDate(getYearStart(getDate()));
    }

    public static int getIntCurrtYearEnd() {
        return getIntDate(getYearEnd(getDate()));
    }

    // ---------------------------------------------------------------------------------------
    public static int getIntCurrtYearMouth() {
        return getIntYearMouth(getDate());
    }

    public static int getIntCurrtYearMouthStart() {
        return getIntCurrtYear() * 100 + 01;
    }

    public static int getIntCurrtYearMouthEnd() {
        return getIntCurrtYear() * 100 + 12;
    }

    // ---------------------------------------------------------------------------------------
    public static int getIntDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        String dateStr = String.valueOf(calendar.get(Calendar.YEAR));
        if (calendar.get(Calendar.MONTH) > 8) {
            dateStr += (calendar.get(Calendar.MONTH) + 1);
        } else {
            dateStr += "0" + (calendar.get(Calendar.MONTH) + 1);
        }
        if (calendar.get(Calendar.DAY_OF_MONTH) > 9) {
            dateStr += calendar.get(Calendar.DAY_OF_MONTH);
        } else {
            dateStr += "0" + calendar.get(Calendar.DAY_OF_MONTH);
        }
        return Integer.parseInt(dateStr);
    }

    public static int getIntYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        String dateStr = String.valueOf(calendar.get(Calendar.YEAR));
        return Integer.parseInt(dateStr);
    }

    public static int getIntMouth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        return (calendar.get(Calendar.MONTH) + 1);
    }

    public static int getIntDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        return (calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static int getIntYear(int date) {
        return date / 10000;
    }

    public static int getIntMouth(int date) {
        return (date / 10000) % 100;
    }

    public static int getIntDay(int date) {
        return date % 100;
    }

    // ---------------------------------------------------------------------------------------
    public static String getStrYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public static String getStrMouth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month < 10) {
            return "0" + month;
        }
        return String.valueOf(month);
    }

    public static String getStrDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        if (day < 10) {
            return "0" + day;
        }
        return String.valueOf(day);
    }

    public static String getStrYear(int date) {
        return String.valueOf(date / 10000);
    }

    public static String getStrMouth(int date) {
        return String.valueOf(date / 100);
    }

    public static String getStrDay(int date) {
        return String.valueOf(date % 100);
    }

    // ---------------------------------------------------------------------------------------
    public static int getIntYearMouth(Date date) {
        return Integer.valueOf(getStrYearMouth(date));
    }

    public static int getIntYearMouth(int date) {
        return date / 100;
    }

    public static String getStrYearMouth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        String dateStr = String.valueOf(calendar.get(Calendar.YEAR));
        if (calendar.get(Calendar.MONTH) > 8) {
            dateStr += (calendar.get(Calendar.MONTH) + 1);
        } else {
            dateStr += "0" + (calendar.get(Calendar.MONTH) + 1);
        }
        return dateStr;
    }

    public static String getStrYearMouth(int date) {
        return String.valueOf(date / 100);
    }

    // ------------------------------------------------------------------------------------------
    public static Date getMonthStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getMonthEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, maxDay);
        return cal.getTime();
    }

    public static int getMonthStartInt(int date) {
        return getIntDate(getMonthStart(parseDate(date)));
    }

    public static int getMonthEndInt(int date) {
        return getIntDate(getMonthEnd(parseDate(date)));
    }

    public static Date getQuarterStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        int month = cal.get(Calendar.MONTH);
        if (month == 0 || month == 1 || month == 2) {
            month = 0;
        } else if (month == 3 || month == 4 || month == 5) {
            month = 3;
        } else if (month == 6 || month == 7 || month == 8) {
            month = 6;
        } else if (month == 9 || month == 10 || month == 11) {
            month = 9;
        }
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getQuarterEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        int month = cal.get(Calendar.MONTH);
        if (month == 0 || month == 1 || month == 2) {
            month = 2;
        } else if (month == 3 || month == 4 || month == 5) {
            month = 5;
        } else if (month == 6 || month == 7 || month == 8) {
            month = 8;
        } else if (month == 9 || month == 10 || month == 11) {
            month = 11;
        }
        cal.set(Calendar.MONTH, month);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, maxDay);
        return cal.getTime();
    }

    public static Date getYearStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getYearEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.set(Calendar.MONTH, 11);
        int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        cal.set(Calendar.DAY_OF_MONTH, maxDay);
        return cal.getTime();
    }

    // ------------------------执行时间计算-------------------------------------------
    public static String calculation(long start) {
        long time = System.currentTimeMillis() - start;
        return time(time);
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

    // ------------------------ ------------------------------------------
    public static String formatDate(Object date) {
        if (date != null) {
            return formatDateTime(date, YYYY_MM_DD);
        }
        return "";
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

    public static Date parseDate(Object date) {
        if (date != null) {
            if (date instanceof Date) {
                return (Date) date;
            } else if (date instanceof String) {
                String dateStr = (String) date;
                String pattern = YYYY_MM_DD;
                if (dateStr.length() == 8) {
                    pattern = YYYYMMDD;
                } else if (dateStr.contains("-") && dateStr.contains(":")) {
                    pattern = YYYY_MM_DD_HH_MM_SS;
                }
                return parseDateTime(dateStr, pattern);
            } else if (date instanceof Integer) {
                int dateInt = (Integer) date;
                if (String.valueOf(dateInt).length() == 8) {
                    return parseDateTime(String.valueOf(date), YYYYMMDD);
                } else if (String.valueOf(dateInt).length() == 6) {
                    return parseDateTime(String.valueOf(date), "yyyyMM");
                } else if (String.valueOf(dateInt).length() == 4) {
                    return parseDateTime(String.valueOf(date), "yyyy");
                } else {
                    throw new RuntimeException("日期不合法");
                }
            } else {
                throw new RuntimeException("日期不合法" + date.toString());
            }
        }
        return null;
    }

    // ------------------------------------------------------------------------------------------
    public static Date parseDateTime(String dateTime, String pattern) {
        DateFormat dateFormat = new java.text.SimpleDateFormat(pattern);
        try {
            return dateFormat.parse(dateTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ------------------------------------------------------------------------------------------
    public static boolean isMonthEnd() {
        return isMonthEnd(getDate());
    }

    public static boolean isMonthEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal.get(Calendar.DAY_OF_MONTH) == cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getMonthMaxDay() {
        Calendar cal = Calendar.getInstance();
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getMonthMaxDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getQuater1234(int date) {
        int year = new Integer(String.valueOf(date).substring(0, 4));
        String month = String.valueOf(date).substring(4);
        if (month.equals("01") || month.equals("02") || month.equals("03")) {
            return year * 100 + 21;
        } else if (month.equals("04") || month.equals("05") || month.equals("06")) {
            return year * 100 + 2;
        } else if (month.equals("07") || month.equals("08") || month.equals("09")) {
            return year * 100 + 3;
        } else if (month.equals("10") || month.equals("11") || month.equals("12")) {
            return year * 100 + 4;
        }
        return 0;
    }

    public static int getQuater1234(Date date) {
        return getQuater1234(getIntYearMouth(date));
    }

    // ------------------------------------------------------------------------------------------
    public static int getDateDiff(int startDate, int endDate) {
        Date start = parseDate(startDate);
        Date end = parseDate(endDate);
        return getDateDiff(start, end);
    }

    public static int getDateDiff(Date startDate, Date endDate) {
        long ds = endDate.getTime() - startDate.getTime();
        return (int) (ds / (24 * 60 * 60 * 1000));
    }

    public static int getDateDiff(int startDate, int endDate, boolean days30) {
        if (days30) {
            int[] rs = getDateDiffYearMonthDay(startDate, endDate);
            return (rs[0] * 12 + rs[1]) * 30 + rs[2];
        } else {
            return getDateDiff(startDate, endDate);
        }
    }

    public static int getDateDiff(Date startDate, Date endDate, boolean days30) {
        if (days30) {
            int[] rs = getDateDiffYearMonthDay(startDate, endDate);
            return (rs[0] * 12 + rs[1]) * 30 + rs[2];
        } else {
            return getDateDiff(startDate, endDate);
        }
    }

    public static int getDateDiffMonth(int startDate, int endDate) {
        return getDateDiffMonth(parseDate(startDate), parseDate(endDate));
    }

    public static int getDateDiffMonth(Date startDate, Date endDate) {
        Calendar startDateCal = Calendar.getInstance();
        startDateCal.setTimeInMillis(startDate.getTime());
        Calendar endDateCal = Calendar.getInstance();
        endDateCal.setTimeInMillis(endDate.getTime());
        int month = (endDateCal.get(Calendar.YEAR) - startDateCal.get(Calendar.YEAR)) * 12;
        return month + endDateCal.get(Calendar.MONTH) - startDateCal.get(Calendar.MONTH);
    }

    public static int[] getDateDiffYearMonthDay(int startDate, int endDate) {
        return getDateDiffYearMonthDay(parseDate(startDate), parseDate(endDate));
    }

    public static int[] getDateDiffYearMonthDay(Date startDate, Date endDate) {
        Calendar startCal = Calendar.getInstance();
        startCal.setTimeInMillis(startDate.getTime());
        Calendar endCal = Calendar.getInstance();
        endCal.setTimeInMillis(endDate.getTime());
        int diffYears = 0, diffMonths = 0, diffDays = 0;
        int startDay = startCal.get(Calendar.DAY_OF_MONTH);
        int endDay = endCal.get(Calendar.DAY_OF_MONTH);
        if (startDay <= endDay) {
            if (isMonthEnd(startCal.getTime())) {
                if (isMonthEnd(endCal.getTime())) {
                    diffMonths = getDateDiffMonth(startCal.getTime(), endCal.getTime());
                    diffDays = 0;
                } else {
                    diffMonths = getDateDiffMonth(startCal.getTime(), endCal.getTime());
                    diffDays = endDay - startDay;
                }
            } else {
                diffMonths = getDateDiffMonth(startCal.getTime(), endCal.getTime());
                diffDays = endDay - startDay;
            }
        } else {
            if (isMonthEnd(startCal.getTime())) {
                if (isMonthEnd(endCal.getTime())) {
                    diffMonths = getDateDiffMonth(startCal.getTime(), endCal.getTime());
                    diffDays = 0;
                } else {
                    endCal.add(Calendar.MONTH, -1);
                    diffMonths = getDateDiffMonth(startCal.getTime(), endCal.getTime());
                }
            } else {
                // 20130130 20130228
                if (isMonthEnd(endCal.getTime())) {
                    diffMonths = getDateDiffMonth(startCal.getTime(), endCal.getTime());
                    diffDays = 0;
                } else {
                    // 20130130 20130227
                    endCal.add(Calendar.MONTH, -1);// 上个月
                    diffMonths = getDateDiffMonth(startCal.getTime(), endCal.getTime());
                    // 获取上个月最大的一天
                    int maxDayOfLastMonth = endCal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    if (maxDayOfLastMonth > startDay) {
                        diffDays = maxDayOfLastMonth - startDay + endDay;
                    } else {
                        diffDays = endDay;
                    }
                }
            }
        }
        diffYears = diffMonths / 12;
        diffMonths = diffMonths % 12;
        return new int[] { diffYears, diffMonths, diffDays };
    }

    public static Date dateAddMonth(int date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(parseDate(date).getTime());
        dateAddMonth(calendar, month);
        return calendar.getTime();
    }

    public static Date dateAddMonth(Date date, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        dateAddMonth(calendar, month);
        return calendar.getTime();
    }

    public static void dateAddMonth(Calendar calendar, int month) {
        if (isMonthEnd(calendar.getTime())) {
            calendar.add(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else {
            calendar.add(Calendar.MONTH, month);
        }
    }

    public static Date dateAddDay(int date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(parseDate(date).getTime());
        dateAddDay(calendar, day);
        return calendar.getTime();
    }

    public static Date dateAddDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        dateAddDay(calendar, day);
        return calendar.getTime();
    }

    public static Date dateAddHour(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime());
        dateAddHour(calendar, day);
        return calendar.getTime();
    }

    public static void dateAddDay(Calendar calendar, int day) {
        calendar.add(Calendar.DAY_OF_MONTH, day);
    }

    public static void dateAddHour(Calendar calendar, int day) {
        calendar.add(Calendar.HOUR_OF_DAY, day);
    }

    //
    public static String getStrDateWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        String str = "";
        switch (dayOfWeek) {
        case 1:
            str = "日";
            break;
        case 2:
            str = "一";
            break;
        case 3:
            str = "二";
            break;
        case 4:
            str = "三";
            break;
        case 5:
            str = "四";
            break;
        case 6:
            str = "五";
            break;
        case 7:
            str = "六";
            break;
        }
        return str;
    }

    /**
    * 方法描述
    * 验证时间格式是否正确
    * @param str
    * @return
    * @创建日期 2016年8月17日
    */
    public static boolean isValidDate(String str) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = (Date) formatter.parse(str);
            return str.equals(formatter.format(date));
        } catch (Exception e) {
            return false;
        }
    }
}
