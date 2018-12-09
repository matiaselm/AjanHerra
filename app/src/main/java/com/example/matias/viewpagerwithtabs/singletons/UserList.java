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

    private UserList() {
        users = new ArrayList<>();
    }

    public void addUser(String name, int yearOfBirth, int sex) {
        users.add(new User(name, yearOfBirth, sex));
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList userList) {
        this.users = userList;
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
