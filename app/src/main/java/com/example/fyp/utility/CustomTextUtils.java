package com.example.fyp.utility;


public class CustomTextUtils {
    private static final String TAG = CustomTextUtils.class.getSimpleName();

    public static boolean isEmpty(String input) {
        if (input != null && input.trim().equalsIgnoreCase("null")) {
            return true;
        }
        return input == null || input.length() == 0;
    }

}
