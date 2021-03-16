package com.homemade.etl.common.utils;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateHelper {

    public static final DateFormat DF_DATETIME = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static final DateFormat DF_ISO_DATETIME = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    public static final DateFormat DF_DAY = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DF_TIME = new SimpleDateFormat("HH:mm:ss");

    /**
     * Gets the String representation for provided date using the DateTime format.
     * {@link DateHelper#DF_DATETIME}
     *
     * @param date
     * @return
     */
    public static String getFormattedDateTime(Date date) {
        return getFormattedDate(date, DF_DATETIME);
    }

    /**
     * Gets the String representation for provided date using the specified format.
     *
     * @param date
     * @param format
     * @return
     */
    public static String getFormattedDate(Date date, DateFormat format) {
        return date == null ? null : format.format(date);
    }

    /**
     * Add specified number (value) of Calendar field objects
     * (year/month/day/hour/minute/second) to provided date.
     *
     * @param date
     * @param calendarField
     * @param value
     * @return
     */
    public static Date add(Date date, int calendarField, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendarField, value);

        return calendar.getTime();
    }

    /**
     * Gets the day starting time of provided Date object.
     *
     * @param date
     * @return
     */
    public static Date getDayStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

}
