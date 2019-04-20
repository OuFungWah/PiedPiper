package com.crazywah.piedpiper.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    static SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyy年MM月dd日");
    static SimpleDateFormat mmdd = new SimpleDateFormat("MM月dd日");
    static SimpleDateFormat hhmmss = new SimpleDateFormat("HH:mm:ss");
    static SimpleDateFormat yyyMMddhhmmss = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    public static final int BIGGER = 1;
    public static final int EQUALS = 0;
    public static final int SMALLER = -1;

    public static final long SEC_LONG = 1000;
    public static final long MIN_LONG = 60 * SEC_LONG;
    public static final long HOUR_LONG = 60 * MIN_LONG;
    public static final long DAY_LONG = 24 * HOUR_LONG;
    public static final long YEAR_LONG = 365 * 24 * DAY_LONG;

    public static int compareTime(long time1, long time2) {
        if (time1 > time2) {
            return BIGGER;
        } else if (time1 == time2) {
            return EQUALS;
        } else {
            return SMALLER;
        }
    }

    public static int compareTime(Date date1, Date date2) {
        return compareTime(date1.getTime(), date2.getTime());
    }

    public static int compareTime(Date date, long time) {
        return compareTime(date.getTime(), time);
    }

    public static int compareTime(long time, Date date) {
        return compareTime(time, date.getTime());
    }

    public static boolean isThisYear(Date date) {
        return isSameYear(date, new Date(System.currentTimeMillis()));
    }

    public static boolean isThisMonth(Date date) {
        return isSameMonth(date, new Date(System.currentTimeMillis()));
    }

    public static boolean isToday(Date date) {
        return isSameDate(date, new Date(System.currentTimeMillis()));
    }

    public static boolean isSameYear(Date date1, Date date2) {
        return date1.getYear() == date2.getYear();
    }

    public static boolean isSameMonth(Date date1, Date date2) {
        return isSameYear(date1, date2) && date1.getMonth() == date2.getMonth();
    }

    public static boolean isSameDate(Date date1, Date date2) {
        return isSameMonth(date1, date2) && date1.getDate() == date2.getDate();
    }

    public static boolean inAYear(Date date1, Date date2) {
        return date1.getTime() - date2.getTime() > YEAR_LONG;
    }

    public static boolean inADay(Date date1, Date date2) {
        return date1.getTime() - date2.getTime() > DAY_LONG;
    }

    public static boolean isLastThreeDay(Date date) {
        return isInNDay(3, date);
    }

    public static boolean isYesteday(Date date) {
        return isInNDay(1, date);
    }

    public static boolean isInNDay(int dayCount, Date date) {
        Calendar now = Calendar.getInstance();
        long thatDay = date.getTime();
        long offset = now.get(Calendar.HOUR_OF_DAY) * HOUR_LONG + now.get(Calendar.MINUTE) * MIN_LONG + now.get(Calendar.SECOND) + SEC_LONG;
        if (now.getTimeInMillis() - offset - thatDay <= dayCount * DAY_LONG) {
            return true;
        } else {
            return false;
        }
    }

    public static String toWeekString(Date date) {
        Calendar thatDay = Calendar.getInstance();
        thatDay.setTime(date);
        switch (thatDay.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.MONDAY:
                return "星期一";
            case Calendar.TUESDAY:
                return "星期二";
            case Calendar.WEDNESDAY:
                return "星期三";
            case Calendar.THURSDAY:
                return "星期四";
            case Calendar.FRIDAY:
                return "星期五";
            case Calendar.SATURDAY:
                return "星期六";
            case Calendar.SUNDAY:
                return "星期日";
            default:
                return "未知";
        }
    }

    public static String formatYMD(Date date) {
        return yyyyMMdd.format(date);
    }

    public static String formatMD(Date date) {
        return mmdd.format(date);
    }

    public static String formatHMS(Date date) {
        return hhmmss.format(date);
    }

    public static String formatYMDHMS(Date date) {
        return yyyMMddhhmmss.format(date);
    }

}
