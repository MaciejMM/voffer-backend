package com.example.freight.utlis;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    private static final String JS_UTC_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private static final String INPUT_JS_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";

    public static String formatDateTime(final String inputDate) {
        final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern(INPUT_JS_DATE_FORMAT);
        final LocalDateTime dateTime = LocalDateTime.parse(inputDate, inputFormatter);
        final DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern(JS_UTC_TIME_FORMAT);
        return dateTime.format(outputFormatter);
    }

}
