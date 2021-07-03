package com.olympiads.utility;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Utility {

    public static Timestamp stringToTimestamp(String date) {
        if (date == null) return null;

        List<Integer> dateList = Arrays.stream(date.replaceAll("[\\.\\-\\/]", " ").split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Calendar gregorianCalendar = GregorianCalendar.getInstance();
        gregorianCalendar.set(dateList.get(2), dateList.get(1) - 1, dateList.get(0));

        return new Timestamp(gregorianCalendar.getTimeInMillis());
    }

    public static String timestampToString(Timestamp date) {
        if (date == null) return null;

        Date newDate = new Date(date.getTime());
        return new SimpleDateFormat("dd.MM.yyyy").format(newDate);
    }
}
