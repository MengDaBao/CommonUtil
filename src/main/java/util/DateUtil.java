package util;

import enums.TimeUnit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateUtil {

    public static ThreadLocal<SimpleDateFormat> datetimeFormat = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));


    /**
     * 获取当月最大日
     */
    public static int getEndMonthDayInt(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当月最大时间
     */
    public static String getEndMonthDayTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return datetimeFormat.get().format(calendar.getTime());
    }

    /**
     * 获取当月最小时间
     */
    public static String getStartMonthDayTime(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return datetimeFormat.get().format(calendar.getTime());
    }

    /**
     * 获取年天数
     */
    public static int getYearDayInt(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
    }

    public static String formatDate(String date) {
        return datetimeFormat.get().format(date);
    }

    public static Date parseDate(String date) throws ParseException {
        return datetimeFormat.get().parse(date);
    }

    /**
     * 返回日期的差
     */
    public static Long getTwoDateSubtract(Date passivity, Date subject, TimeUnit unit) {
        long time1 = passivity.getTime();
        long time2 = subject.getTime();
        Calendar calendar1 = new GregorianCalendar();
        calendar1.setTime(passivity);
        Calendar calendar2 = new GregorianCalendar();
        calendar2.setTime(subject);
        switch (unit) {
            case MILLISECOND:
            case SECOND:
                time1 = time1 / 1000;
                time2 = time2 / 1000;
                break;
            case MINUTE:
                time1 = time1 / 1000 / 60;
                time2 = time2 / 1000 / 60;
                break;
            case HOUR:
                time1 = time1 / 1000 / 60 / 60;
                time2 = time2 / 1000 / 60 / 60;
                break;
            case DAY:
                time1 = time1 / 1000 / 60 / 60 / 24;
                time2 = time2 / 1000 / 60 / 60 / 24;
                break;
            case MONTH:
                time1 = (calendar1.get(Calendar.YEAR) * 12L) + calendar1.get(Calendar.MONTH);
                time2 = (calendar2.get(Calendar.YEAR) * 12L) + calendar2.get(Calendar.MONTH);
                break;
            case YEAR:
                time1 = calendar1.get(Calendar.YEAR);
                time2 = calendar2.get(Calendar.YEAR);
                break;
            case CENTURY:
                time1 = calendar1.get(Calendar.YEAR) / 100;
                time2 = calendar2.get(Calendar.YEAR) / 100;
                break;
        }
        return time1 - time2;
    }

    /**
     * 返回较大日期
     */
    public static Date getMaxDate(Date date1, Date date2, TimeUnit unit) {
        long subtract = getTwoDateSubtract(date1, date2, unit);
        if (subtract > 0) {
            return date1;
        } else if (subtract < 0) {
            return date2;
        } else {
            return null;
        }
    }

    /**
     * 返回较小日期
     */
    public static Date getMinDate(TimeUnit unit, Date date1, Date date2) {
        long subtract = getTwoDateSubtract(date1, date2, unit);
        if (subtract > 0) {
            return date2;
        } else if (subtract < 0) {
            return date1;
        } else {
            return null;
        }
    }


    /**
     * 返回两日期绝对值
     */
    public static Long getTwoDateAbs(Date date1, Date date2, TimeUnit unit) {
        long subtract = getTwoDateSubtract(date1, date2, unit);
        return Math.abs(subtract);
    }



}
