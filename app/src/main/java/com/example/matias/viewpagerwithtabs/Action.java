package com.example.matias.viewpagerwithtabs;

import android.util.Log;

public class Action {
    private String type;
    private int time;
    private int mins;
    private double hours;
    private boolean needMoreThan;
    private double refHours;
    private double resultHours;

    public Action(String type, boolean needMoreThan, double refHours) {
        this.type = type;
        this.time = 0;
        this.mins = 0;
        this.hours = 0;
        this.needMoreThan = needMoreThan;
        this.refHours = refHours;
        this.resultHours = 0;
    }

    public void setRefHours(double refHours){this.refHours = refHours; }

    public void setNeedMoreThan(boolean needMoreThan){this.needMoreThan = needMoreThan; }

    public void setType(String type){this.type = type; }

    public int getTime() {
        return time;
    }

    public void addTime(int lenghtMinutes){
        time = time + lenghtMinutes;
        mins = time % 60;
        hours = time / 60;
        resultHours = refHours - hours;
        if(refHours == 0){
            resultHours = 0;
        }
        Log.d("Sovellus", this + " incremented " + lenghtMinutes + " minutes");
    }

    public String getInfo(){
        return Double.toString(refHours) + " h | " + Double.toString(hours) + " h | " + Double.toString(resultHours) +" h";
    }

    public String getType(){
        return type;
    }

    @Override
    public String toString() {
        return type;

    }
}