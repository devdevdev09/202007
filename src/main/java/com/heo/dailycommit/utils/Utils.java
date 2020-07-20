package com.heo.dailycommit.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static String getToday() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar today = Calendar.getInstance();

        return sdf.format(today.getTime());
    }

    public static String getToday(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar today = Calendar.getInstance();

        return sdf.format(today.getTime());
    }

    public static String getDate(String pattern, int count) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Calendar today = Calendar.getInstance();

        today.add(Calendar.DATE, count * -1);
        return sdf.format(today.getTime());
    }

    public static String getDate(int count) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar today = Calendar.getInstance();

        today.add(Calendar.DATE, count * -1);
        return sdf.format(today.getTime());
    }

    public static String getDate(String strDate) {
        DateFormat dffrom = new SimpleDateFormat("M/dd/yyyy");
        DateFormat dfto = new SimpleDateFormat("yyyy-MM-dd");  
        Date today;
        Calendar cal = Calendar.getInstance();
        try {
            today = dffrom.parse(strDate);
            cal.setTime(today);
            cal.add(Calendar.DATE, -5);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dfto.format(cal.getTime());
    }
}