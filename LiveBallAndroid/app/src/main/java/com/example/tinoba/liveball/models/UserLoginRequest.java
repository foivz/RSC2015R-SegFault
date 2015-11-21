package com.example.tinoba.liveball.models;

/**
 * Created by antonio on 21/11/15.
 */
public class UserLoginRequest {
    private String user;
    private String pass;

    public UserLoginRequest(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
