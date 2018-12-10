package com.example.matias.viewpagerwithtabs.classes;

import android.util.Log;

import java.text.DecimalFormat;

public class Action {
    private String type;
    private double totalTimeMinutes;
    private double averageHours;
    private boolean needMoreThan;
    private double referenceHours;
    private static DecimalFormat twoDigit = new DecimalFormat("#.##");
    private String description;
    private long currentTime;
    private long firstTime;

    public Action(String type, boolean needMoreThan, double refHours, String description) {
        this.type = type;
        this.totalTimeMinutes = 0;
        this.averageHours = 0;
        this.needMoreThan = needMoreThan;
        this.referenceHours = refHours;
        this.description = description;
        this.currentTime = 0;
    }

    public void setRefHours(double refHours) {
        this.referenceHours = refHours;
    }

    public void setNeedMoreThan(boolean needMoreThan) {
        this.needMoreThan = needMoreThan;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getTotalTimeMinutes() {
        return totalTimeMinutes;
    }

    public void addTime(int lenghtMinutes) {
        totalTimeMinutes = totalTimeMinutes + lenghtMinutes;

        if (firstTime == 0){
            this.firstTime = System.currentTimeMillis();
        }
        Log.d("Sovellus", this + " incremented " + lenghtMinutes + " minutes");
    }
/*
    public String getInfo() {
        String outRefHours = twoDigit.format(referenceHours);
        String outHours = twoDigit.format(averageHours);
        return outRefHours + " h | " + outHours + " h";
    }
*/
    public String getTimeReference() {
        if (referenceHours == -1) {
            return "Ei tavoitetta";
        } else {
            if (needMoreThan) {
                return "Tavoite yli " + twoDigit.format(referenceHours) + "h";
            } else {
                return "Tavoite alle " + twoDigit.format(referenceHours) + "h";
            }
        }
    }

    public String getTimeAverage() {
        this.currentTime = System.currentTimeMillis();
        long days = (currentTime - firstTime) / (1000 * 60 * 60 * 24);

        if(days < 1){ days = 1; }
        
        averageHours = totalTimeMinutes / 60 / days;
        if (averageHours > 24) {
            averageHours = 24;
        }

        return "Keskiarvosi " + twoDigit.format(averageHours) + "h";
    }

    public String getTimeAverageLv() {
        this.currentTime = System.currentTimeMillis();
        long days = (currentTime - firstTime) / 1000 / 60 / 60 / 24;

        Log.d("Sovellus", "Current time: " + String.valueOf(currentTime));
        Log.d("Sovellus", "First time: " + String.valueOf(firstTime));

        Log.d("Sovellus", "getTimeAverage 1st " + days);

        if(days < 1){ days = 1; }

        averageHours = totalTimeMinutes / 60 / days;
        if (averageHours > 24) {
            averageHours = 24;

        }

        Log.d("Sovellus", "getTimeAverage 2nd " + days);
        return twoDigit.format(averageHours) + "h";
    }

    public String getTimeResult() {
        if (referenceHours == -1) {
            return "Sopiva";
        } else {
            if (needMoreThan == true) {

                if (averageHours > 20) {
                    return "Vääristynyt";
                } else if (referenceHours > averageHours) {
                    return "Alle tavoitteen";
                } else {
                    return "Sopiva";
                }
            } else {
                if (averageHours > 20) {
                    return "Vääristynyt";
                } else if (referenceHours < averageHours) {
                    return "Yli tavoitteen";
                } else {
                    return "Sopiva";
                }
            }
        }
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;

    }
}