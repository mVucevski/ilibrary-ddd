package com.mvucevski.usermanagement.exception;

public class UsernameDoesntExistExceptionResponse {
    private String username;

    public UsernameDoesntExistExceptionResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
