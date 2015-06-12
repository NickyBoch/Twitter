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

    public UserData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

}
