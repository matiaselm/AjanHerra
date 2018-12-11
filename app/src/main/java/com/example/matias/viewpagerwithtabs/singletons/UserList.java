package com.example.matias.viewpagerwithtabs.singletons;

import com.example.matias.viewpagerwithtabs.classes.User;

import java.util.ArrayList;

/**
 * This singleton manages all users as a list. The user list is saved and loaded as gson in saved preference. When new user is created we immediately save the preference.
 * When an user logs in we keep track of the user index in a our UserList with currentUser. This provides base for almost all code transactions.
 */
public class UserList {

    private ArrayList<User> users;
    private int intCurrentUser;

    private static final UserList ourInstance = new UserList();

    public static UserList getInstance() {
        return ourInstance;
    }

    /**
     * Create empty userlist.
     */
    private UserList() {
        users = new ArrayList<>();
    }

    /**
     * Create new user by given parameters into Userlist Arraylist
     *
     * @param name
     * @param yearOfBirth
     * @param sex
     */
    public void addUser(String name, int yearOfBirth, int sex) {
        users.add(new User(name, yearOfBirth, sex));
    }

    public void removeUser() {
        users.remove(intCurrentUser);
    }

    /**
     * Get UserList as arraylist
     *
     * @return Arraylist
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Feed Arraylist from sharedprefs
     *
     * @param userList Arraylist
     */
    public void setUsers(ArrayList userList) {
        this.users = userList;
    }

    /**
     * Get User object with requested index
     *
     * @param index
     * @return User
     */
    public User getUser(int index) {
        return getUsers().get(index);
    }

    /**
     * Check if the username is taken
     *
     * @param testName
     * @return
     */
    public boolean testNewUserName(String testName) {

        for (int i = 0; (getUsers().size()) > i; i++) {
            if (testName.equals(getUser(i).getName())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get currently selected users index
     *
     * @return index
     */
    public int getCurrentUserInt() {
        return intCurrentUser;
    }

    /**
     * Check if the username is taken but ignore if the user tries to put it's own name
     *
     * @param testName
     * @return
     */
    public boolean testSettingsUserName(String testName) {

        for (int i = 0; (getUsers().size()) > i; i++) {
            if (testName.equals(getUser(i).getName()) && i != intCurrentUser) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get current User object with current user index
     *
     * @return
     */
    public User getCurrentUser() {
        return getUsers().get(intCurrentUser);
    }

    /**
     * Set current user index
     *
     * @param IntCurrentUser
     */
    public void setCurrentUser(int IntCurrentUser) {
        this.intCurrentUser = IntCurrentUser;
    }
}
