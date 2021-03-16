package com.homemade.etl.common.utils;


public interface Formats {

    // Default formatting pattern for Date Time fields of DTO objects
    String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss Z";

    // Default formatting pattern for ISO Local Date Time fields of DTO objects
    String DATE_TIME_ISO_LOCAL_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    // Default formatting pattern for Date fields of DTO objects
    String DATE_FORMAT = "yyyy-MM-dd";

    // Default Time Zone for Date fields of DTO objects
    String TIME_ZONE = "CET";

}
