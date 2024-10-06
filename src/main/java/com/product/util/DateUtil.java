package com.product.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String currentDateAsString(){
        return LocalDate.now().format(dateTimeFormatter());
    }

    public static DateTimeFormatter dateTimeFormatter(){
        return dateFormatter(DATE_FORMAT);
    }

    public static DateTimeFormatter dateFormatter(String pattern){
        return DateTimeFormatter.ofPattern(pattern);
    }

}
