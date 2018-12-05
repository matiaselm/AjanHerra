package com.example.matias.viewpagerwithtabs;

import android.util.Log;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TimeDifference {

    private int days;
    private int hours;
    private int min;
    private int time;
    DateTimeFormatter dateTimeFormatter;
    SimpleDateFormat startDateIn;

    private int getTime(String startDate, String endDate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        startDateIn = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date startDateFormated = startDateIn.parse(startDate);
        Date endDateFormated = new simpleDateFormat.parse(endDate);


        startDateIn.parse(startDate);

        long difference = endDate.getTime() - startDate.getTime();
        if (difference < 0) {
            Date dateMax = simpleDateFormat.parse("24:00");
            Date dateMin = simpleDateFormat.parse("00:00");
            difference = (dateMax.getTime() - startDate.getTime()) + (endDate.getTime() - dateMin.getTime());
        }

        min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);

        Log.d("log_tag", "Hours: " + hours + min);
        return min;
    }
}

