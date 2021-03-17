package com.homemade.etl.common.utils;


/**
 * Utility methods for data manipulation with Strings.
 */
public class StringHelper {

	public static boolean isBlank(String val) {
        return val == null || val.length() == 0;
    }

}
