package com.mvucevski.usermanagement.exception;


public class InvalidLoginResponse {

    private String username;
    private String password;

    public InvalidLoginResponse(){
        this.username = "Your username and password don't match";
        this.password = "Your username and password don't match";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}