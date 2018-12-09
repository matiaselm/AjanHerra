package com.example.matias.viewpagerwithtabs.singletons;

import com.example.matias.viewpagerwithtabs.classes.User;

import java.util.ArrayList;

public class UserList {

    private ArrayList<User> users;
    private User currentUser;
    private int intCurrentUser;
    private int userListNumber;

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
     * @param name
     * @param yearOfBirth
     * @param sex
     */
    public void addUser(String name, int yearOfBirth, int sex) {
        users.add(new User(name, yearOfBirth, sex));
    }

    /**
     * Get UserList as arraylist
     * @return Arraylist
     */
    public ArrayList<User> getUsers() {
        return users;
    }

    /**
     * Feed Arraylist from sharedprefs
     * @param userList Arraylist
     */
    public void setUsers(ArrayList userList) {
        this.users = userList;
    }

    /**
     * Get User object with requested index
     * @param index
     * @return User
     */
    public User getUser(int index) {
        return getUsers().get(index);
    }

    /**
     * Check if the username is taken
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
     * Check if the username is taken but ignore if the user tries to put it's own name
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
     * @return
     */
    public User getCurrentUser() {
        return getUsers().get(intCurrentUser);
    }

    /**
     * Set current user index
     * @param IntCurrentUser
     */
    public void setCurrentUser(int IntCurrentUser) {
        this.intCurrentUser = IntCurrentUser;
    }
}
