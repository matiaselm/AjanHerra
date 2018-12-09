package com.example.matias.viewpagerwithtabs.classes;

import android.text.format.Time;
import android.util.Log;


public class User {
    private String name;
    private int age;
    private int yearOfBirth;
    private String sex;
    private int sexInt;

    /**
     *
     * @param name
     * @param yearOfBirth
     * @param sexInt 0=mies, 1=nainen, 2=muu
     */
    public User(String name, int yearOfBirth, int sexInt) {
        this.name = name;
        this.yearOfBirth = yearOfBirth;

        setSex(sexInt);
    }

    public User() {
        this( "Name", 0, 2);
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

    public int getSexInt() {
        return sexInt;
    }

    public void setSex(int sexInt) {

        if (sexInt == 0){
            this.sex = "Mies";
        } else if (sexInt == 1){
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
