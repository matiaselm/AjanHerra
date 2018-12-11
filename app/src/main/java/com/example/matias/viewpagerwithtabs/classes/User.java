package com.example.matias.viewpagerwithtabs.classes;

import android.text.format.Time;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represent one user in the app. User are saved into an arraylist in UserList singleton.
 */
public class User {
    private String name;
    private int age;
    private int yearOfBirth;
    private String sex;
    private int sexInt;
    private long FirstLoginTime;
    private List<String> historyList;

    /**
     * New user is created
     *
     * @param name        Name of user
     * @param yearOfBirth Year of birth
     * @param sexInt      0=mies, 1=nainen, 2=muu
     */
    public User(String name, int yearOfBirth, int sexInt) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.sexInt = sexInt;

        historyList = new ArrayList<>();

        setSex(this.sexInt);
    }

    /**
     * Add a new log entry for the history log. Sort the list in reverse order. Summary is the feed that comes from adding time.
     *
     * @param summary from adding new action event
     */
    public void addHistoryEvent(String summary) {

        historyList.add(summary);
        Collections.sort(historyList);
        Collections.reverse(historyList);
    }

    /**
     * Set new year of birth
     *
     * @param yearOfBirth
     */
    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    /**
     * Get the name of user
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set new name for the user
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Calculate age. Age is roughly calculated by current year - year of birth.
     *
     * @return age
     */
    public int getAge() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int currentYear = today.year;
        this.age = currentYear - yearOfBirth;
        Log.d("Sovel", Integer.toString(currentYear) + Integer.toString(yearOfBirth));

        return this.age;
    }

    /**
     * Get the history log from user. Every time new action input is added, a log entry is added.
     *
     * @return List of history log strings
     */
    public List<String> getHistoryList() {
        return historyList;
    }

    /**
     * Get the time in millis user created an account.
     *
     * @return long millis
     */
    public long getFirstLoginTime() {
        return FirstLoginTime;
    }

    /**
     * Set the time in millis user created an account.
     *
     * @param firstLoginTime
     */
    public void setFirstLoginTime(long firstLoginTime) {
        FirstLoginTime = firstLoginTime;
    }

    /**
     * Get year of birth
     *
     * @return int year of birth
     */
    public int getYearOfBirth() {
        return this.yearOfBirth;
    }

    /**
     * Get gender as string
     *
     * @return geneder
     */
    public String getSex() {
        return this.sex;
    }

    /**
     * Get sex as integer for listing
     * 0=male
     * 1=female
     * 2=other
     *
     * @return int gender
     */
    public int getSexInt() {
        return this.sexInt;
    }

    /**
     * Set sex as string based on list integer
     * 0=male
     * 1=female
     * 2=other
     *
     * @return int gender
     */
    public void setSex(int sexInt) {
        this.sexInt = sexInt;
        if (sexInt == 0) {
            this.sex = "Mies";
        } else if (sexInt == 1) {
            this.sex = "Nainen";
        } else {
            this.sex = "Muu";
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
