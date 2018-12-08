package com.example.matias.viewpagerwithtabs;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class UserList {

    private ArrayList<User> users;
    private User currentUser;
    private int intCurrentUser;

    private static final UserList ourInstance = new UserList();

    public static UserList getInstance() {
        return ourInstance;
    }

    private UserList() {
        users = new ArrayList<>();
    }

    public void addUser(String name, int yearOfBirth, String sex) {
        users.add(new User(name, yearOfBirth, sex));
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public User getUser(int index) {
        return getUsers().get(index);
    }

    public boolean testNewUserName(String testName) {

        for (int i = 0; (getUsers().size()) > i; i++) {
            if (testName.equals(getUser(i).getName())) {
                return false;
            }
        }
        return true;
    }

    public User getCurrentUser() {
        return getUsers().get(intCurrentUser);
    }

    public void setCurrentUser(int IntCurrentUser) {
        this.intCurrentUser = IntCurrentUser;
    }
}
