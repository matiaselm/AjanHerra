package com.example.matias.viewpagerwithtabs;

import android.util.Log;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
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
    private Long dateMaxLong, dateMinLong, startDateLong, endDateLong;
    private String dateMaxString, dateMinString;

    public String getTimeDifference(String startDate, String endDate) {

        Log.d("time", startDate + endDate);
        return"moi";
     /*   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        startDateIn = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        long difference = Integer.parseInt(endDate) - Integer.parseInt(startDate);
        if (difference < 0) {

            Date dateMax = null;

            try { dateMax = simpleDateFormat.parse("24:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Date dateMin = null;

            try {
                dateMin = simpleDateFormat.parse("00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }

            dateMaxString = simpleDateFormat.format(dateMax);
            dateMinString = simpleDateFormat.format(dateMin);

            dateMinLong = Long.parseLong(dateMinString);
            dateMaxLong = Long.parseLong(dateMaxString);
            startDateLong = Long.parseLong(startDate);
            endDateLong = Long.parseLong(endDate);

            difference = (dateMaxLong - startDateLong) + (endDateLong - dateMinLong);

            return Long.toString(difference);
        } else{
            Log.d("time", "no");
            return "E";
        }

        /*min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);

        Log.d("log_tag", "Hours: " + hours + min);
        return min;*/
  }
}

