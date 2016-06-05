package com.mygaienko.pmgmt.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Created by enda1n on 05.06.2016.
 */
public class DateUtils {

    public static final String DATE_PATTERN = "yyyy-MMM-dd";
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern(DATE_PATTERN);

    public static DateTime parseDate(String date) {
        return DateTime.parse(date, DATE_FORMATTER);
    }

    public static String dateToString(DateTime date) {
        return date.toString(DATE_FORMATTER);
    }
}
