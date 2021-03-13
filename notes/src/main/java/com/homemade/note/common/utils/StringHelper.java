package com.homemade.note.common.utils;


public class StringHelper {

    public static final String EMAIL_REGEX = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

    public static boolean isBlank(String val) {
        return val == null || val.length() == 0;
    }

    public static boolean isNotBlank(String val) {
        return !isBlank(val);
    }

}
