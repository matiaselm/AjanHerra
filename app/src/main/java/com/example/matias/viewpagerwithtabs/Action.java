package com.example.matias.viewpagerwithtabs;

import android.util.Log;

import java.text.DecimalFormat;

public class Action {
    private String type;
    private double time;
    private double mins;
    private double hours;
    private boolean needMoreThan;
    private double referenceHours;
    private double resultHours;
    private static DecimalFormat twoDigit = new DecimalFormat("#.##");
    private String description;

    public Action(String type, boolean needMoreThan, double refHours, String description) {
        this.type = type;
        this.time = 0;
        this.mins = 0;
        this.hours = 0;
        this.needMoreThan = needMoreThan;
        this.referenceHours = refHours;
        this.resultHours = 0;
        this.description = description;

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

    public double getTime() {
        return time;
    }


    public void addTime(int lenghtMinutes) {
        time = time + lenghtMinutes;
        mins = time % 60;
        hours = time / 60;
        resultHours = referenceHours - hours;
        if (referenceHours == 0) {
            resultHours = 0;
        }
        Log.d("Sovellus", this + " incremented " + lenghtMinutes + " minutes");
    }

    public String getInfo() {
        return twoDigit.format(referenceHours) + " h | " + twoDigit.format(hours) + " h | " + twoDigit.format(resultHours) + " h";
    }

    public String getTimeReference() {
        if (referenceHours == -1) {
            return "Ei suositusta";
        } else {
            if (needMoreThan == true) {
                return "Suositus yli " + twoDigit.format(referenceHours) + "h";
            } else {
                return "Suositus alle " + twoDigit.format(referenceHours) + "h";
            }
        }
    }

    public String getTimeAverage() {
        return "Keskiarvosi " + twoDigit.format(hours) + "h";
    }

    public String getTimeResult() {
        if (referenceHours == -1) {
            return "Sopiva";
        } else {
            if (needMoreThan == true) {

                if (referenceHours > resultHours) {
                    return "Alle suosituksen";
                } else {
                    return "Sopiva";
                }
            } else {
                if (referenceHours < resultHours) {
                    return "Yli suosituksen";
                } else {
                    return "Sopiva";
                }
            }
        }
    }

    public String getDescription(){
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