package com.example.matias.viewpagerwithtabs.classes;

import android.util.Log;

import com.example.matias.viewpagerwithtabs.singletons.UserList;

import java.text.DecimalFormat;

/**
 * This class creates objects that represent user activity per category.
 */
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

    /**
     * New action type constructed.
     *
     * @param type         Name of the action
     * @param needMoreThan Should spend more time on the action than refHours?
     * @param refHours     The amount of hours suggested.
     * @param description  The description that is displayed when selecting the action
     */
    public Action(String type, boolean needMoreThan, double refHours, String description) {
        this.type = type;
        this.totalTimeMinutes = 0;
        this.averageHours = 0;
        this.needMoreThan = needMoreThan;
        this.referenceHours = refHours;
        this.description = description;
        this.currentTime = 0;
    }

    /**
     * Set the new type for an action.
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Add new time to the action. Also check that if the first time logging in date was saved.
     *
     * @param lenghtMinutes
     */
    public void addTime(int lenghtMinutes) {
        totalTimeMinutes = totalTimeMinutes + lenghtMinutes;

        if (firstTime == 0) {
            this.firstTime = System.currentTimeMillis();
        }
        Log.d("Sovellus", this + " incremented " + lenghtMinutes + " minutes");
    }

    /**
     * Get suggested time as strings
     *
     * @return
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

    /**
     * First we get the first login date, calculate how many days we have been using the app ->
     * Total time divided by rounded down days after first login(1,99 = 1) ->
     * Calculates and returns average hours ->
     *
     * @return Average hours
     */
    public double calculateAverageTime() {
        firstTime = UserList.getInstance().getCurrentUser().getFirstLoginTime();
        currentTime = System.currentTimeMillis();
        double totalTime = (currentTime - firstTime) / 1000.0 / 60.0 / 60.0 / 24.0;
        //It's been a ...
        long days = (long) totalTime;

        /**
         * Cannot divide with 0
         */
        if (days < 1) {
            days = 1;
        }

        averageHours = totalTimeMinutes / 60.0 / days;

        /**
         * If we have a wrong amount of time added.
         */
        if (averageHours > 24) {
            averageHours = 24;
        }

        Log.d("Sovellus", "Day calculator = (" + currentTime + "-" + firstTime + ") / 1000 / 60 / 60 / 24 =" + totalTime + "=" + days + "days");
        Log.d("Sovellus", "Average hours = " + totalTimeMinutes + "min / 60 / " + days + "days =" + averageHours);

        return averageHours;
    }

    /**
     * Get Average time user spent on action in long format.
     *
     * @return Long format of average time as String
     */
    public String getTimeAverage() {
        return "Keskiarvosi " + twoDigit.format(calculateAverageTime()) + "h";
    }

    /**
     * Get Average time user spent on action in short format. This is used for listview
     *
     * @return Long format of average time as String
     */
    public String getTimeAverageLv() {
        return twoDigit.format(calculateAverageTime()) + "h";
    }

    /**
     * Check if we have managed to stay with suggested time.
     *
     * @return success
     */
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

    /**
     * Get the description string of an action.
     *
     * @return String description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Get the type name string of an action.
     *
     * @return String type
     */
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;

    }
}