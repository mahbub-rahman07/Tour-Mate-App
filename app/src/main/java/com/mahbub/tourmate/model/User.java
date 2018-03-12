package com.mahbub.tourmate.model;

import android.content.ContentValues;

import com.mahbub.tourmate.database.DatabaseHelper;

/**
 * Created by Mahbuburrahman on 2/11/18.
 */

public class User {
    private int userId;
    private String userAuth;
    private String userName;
    private String userMail;
    private String userPass;

    public User() {
    }

    public User(String userName, String userMail, String userPass) {
        this.userName = userName;
        this.userMail = userMail;
        this.userPass = userPass;
    }

    public User(int userId, String userAuth, String userName, String userMail) {
        this.userId = userId;
        this.userAuth = userAuth;
        this.userName = userName;
        this.userMail = userMail;
    }

    public User(int userId, String userAuth, String userName, String userMail, String userPass) {
        this.userId = userId;
        this.userAuth = userAuth;
        this.userName = userName;
        this.userMail = userMail;
        this.userPass = userPass;
    }

    public User(String userAuth, String userName, String userMail, String userPass) {
        this.userAuth = userAuth;
        this.userName = userName;
        this.userMail = userMail;
        this.userPass = userPass;
    }

    public String getUserAuth() {
        return userAuth;
    }

    public void setUserAuth(String userAuth) {
        this.userAuth = userAuth;
    }

    public String getUserPass() {
        return userPass;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userMail='" + userMail + '\'' +
                '}';
    }
    public ContentValues toValues(){
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COL_USER_NAME, getUserName());
        values.put(DatabaseHelper.COL_USER_MAIL, getUserMail());
        values.put(DatabaseHelper.COL_USER_AUTH, getUserAuth());
        values.put(DatabaseHelper.COL_USER_PASSWORD, getUserPass());
        return values;
    }
}
