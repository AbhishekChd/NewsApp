package com.example.abhishek.newsapp.utils;

import java.sql.Timestamp;

import timber.log.Timber;

public class BindingUtils {
    /**
     * @param utcTimeString Time in UTC:+00 - Example: 2018-05-10T10:13:00Z
     * @return Formatted String of time elapsed by now in min/hrs/days
     */
    public static String getElapsedTime(long utcTimeString) {
        long timeElapsedInSeconds = (System.currentTimeMillis() - utcTimeString) / 1000;

        if (timeElapsedInSeconds < 60) {
            return "less than 1m";
        } else if (timeElapsedInSeconds < 3600) {
            timeElapsedInSeconds = timeElapsedInSeconds / 60;
            return timeElapsedInSeconds + "m";
        } else if (timeElapsedInSeconds < 86400) {
            timeElapsedInSeconds = timeElapsedInSeconds / 3600;
            return timeElapsedInSeconds + "h";
        } else {
            timeElapsedInSeconds = timeElapsedInSeconds / 86400;
            return timeElapsedInSeconds + "d";
        }
    }

    //    @{news.source.name}
    public static String getSourceAndTime(String sourceName, Timestamp date) {
        Timber.d("Date : %s", date.toString());
        Timber.d("Date : %s", date.getTime());
        StringBuilder builder = new StringBuilder();
        builder.append(sourceName)
                .append(" • ")
                .append(getElapsedTime(date.getTime()));
        return builder.toString();
    }
}


