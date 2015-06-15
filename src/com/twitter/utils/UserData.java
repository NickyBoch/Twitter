package com.twitter.utils;

/**
 * Created
 * by:   Admin
 * date: 12.06.2015
 * time: 9:36
 */
public class UserData {
    private String login;
    private String password;

    private String userName;

    public UserData(String login, String password,String userName) {
        this.login = login;
        this.password = password;
        this.userName = userName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

}
