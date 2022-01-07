package com.example.fyp.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {
    public static final String TAG = DateUtils.class.getSimpleName();

    // date time format in settings page
    public static final String TIME_FORMAT_12_HOUR = "h:mm a";
    public static final String TIME_FORMAT_24_HOUR = "HH:mm";
    public static final String DATE_FORMAT_1 = "MMMM dd, yyyy";
    public static final String DATE_FORMAT_2 = "dd MMMM, yyyy";
    public static final String DATE_FORMAT_3 = "yyyy, MMMM dd";

    // other format
    public static final String DATE_FORMAT_4 = "dd MMM yyyy";
    public static final String DATE_FORMAT_5 = "dd MMM yyyy, hh:mm a";
    public static final String DATE_FORMAT_6 = "MMMM yyyy";
    public static final String DATE_FORMAT_7 = "yyyy-MM-dd";
    public static final String DATE_FORMAT_8 = "EEEE";
    public static final String DATE_FORMAT_9 = "MM";
    public static final String DATE_FORMAT_10 = "yyyy";
    public static final String DATE_FORMAT_11 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_12 = "dd/MM/yy";
    public static final String DATE_FORMAT_13 = "dd/MM";
    public static final String DATE_FORMAT_14 = "dd/MM/yyyy";


    public static String formatDate(String inputPattern, String outputPattern, String input) {
        String results = "";

        try {
            Date date = new SimpleDateFormat(inputPattern, Locale.ENGLISH).parse(input);
            SimpleDateFormat sdf = new SimpleDateFormat(outputPattern, Locale.ENGLISH);
            results = sdf.format(date);
        } catch (Exception e) {

            return input;
        }

        return results;
    }

    public static Date getDate(String inputPattern, String input) {
        Date date = new Date();

        try {
            date = new SimpleDateFormat(inputPattern, Locale.ENGLISH).parse(input);
        } catch (Exception e) {
        }

        return date;
    }

    public static String getDateStr(String outputPattern, Date input) {
        String results = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(outputPattern, Locale.ENGLISH);
            results = sdf.format(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return results;
    }

    public static Date getCurrentDate() {
        Calendar calendar = Calendar.getInstance();

        return calendar.getTime();
    }

    public static String getCurrentDateStr(String outputPattern) {
        String results = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(outputPattern, Locale.ENGLISH);
            results = sdf.format(getCurrentDate());
        } catch (Exception e) {
        }

        return results;
    }

    public static String getDayOfMonth(String inputPattern, String input) {
        // input ~ 8 Apr 2019 will return 8
        String results = "";

        try {
            Date date = new SimpleDateFormat(inputPattern, Locale.ENGLISH).parse(input);
            SimpleDateFormat sdf = new SimpleDateFormat("d", Locale.ENGLISH);
            results = sdf.format(date);
        } catch (Exception e) {

            return input;
        }

        return results;
    }

    public static boolean isToday(String inputPattern, String input) {
        String today = getCurrentDateStr(DATE_FORMAT_4);
        input = formatDate(inputPattern, DATE_FORMAT_4, input);

        return today.equals(input);
    }

    public static int compareWithToday(String inputPattern, String input) {
        Date inputDate = new Date();
        Date today = new Date();


        try {
            inputDate = new SimpleDateFormat(inputPattern, Locale.ENGLISH).parse(input);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // -1 ~ input date is before current
        // 0  ~ input date is equal current
        // 1  ~ input date is after current

        int results = -1;

        if (inputDate.after(today)) {
            results = 1;
        } else if (inputDate.equals(today)) {
            results = 0;
        } else {
            results = -1;
        }


        return results;
    }
}
