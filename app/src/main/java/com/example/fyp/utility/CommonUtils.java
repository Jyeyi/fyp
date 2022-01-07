package com.example.fyp.utility;

import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;


public class CommonUtils {
    public static final String TAG = CommonUtils.class.getSimpleName();


    public static void printExceptionTrace(String tag, Exception e) {
        Timber.tag(tag).d(e);
    }

    public static void printSimpleException(String tag, Exception e) {
        Timber.tag(tag).d(e.getMessage());
    }

    public static void printLongLog(String message) {
        int maxLogSize = 2000;
        for(int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            Timber.d(message.substring(start, end));
        }
    }

    public static void setSpinnerOffset(Spinner spinner) {
        // set dropdown to place below the spinner
        spinner.post(() -> {
            spinner.setDropDownVerticalOffset(spinner.getDropDownVerticalOffset()
                    + spinner.getHeight());
        });
    }


}
