package com.example.matias.viewpagerwithtabs;

import android.text.format.Time;
import android.util.Log;

public class User {
    private String name;
    private int age;
    private int yearOfBirth;
    private String sex;

    public User(String name, int yearOfBirth, String sex) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;
        this.sex = sex;
    }

    public User() {
        this.name = "Name";
        this.age = 0;
        this.yearOfBirth = 0;
        this.sex = "n";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        Time today = new Time(Time.getCurrentTimezone());
        today.setToNow();
        int currentYear = today.year;
        this.age = today.year - yearOfBirth;

        Log.d("Sovellus", Integer.toString(this.age));
        return this.age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return name;
    }
}
