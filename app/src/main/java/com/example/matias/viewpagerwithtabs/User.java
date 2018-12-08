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

        setSex(sex);
    }

    public User() {
        this.name = "Name";
        this.age = 0;
        this.yearOfBirth = 0;
        this.sex = "o";
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
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
        this.age = currentYear - yearOfBirth;
        Log.d("Sovel", Integer.toString(currentYear) + Integer.toString(yearOfBirth));

        return this.age;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {

        if (sex.equals("f")){
            this.sex = "Nainen";
        } else if (sex.equals("m")){
            this.sex = "Mies";
        } else {
            this.sex = "Muu";
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
