package com.mvucevski.usermanagement.api.payload;

import lombok.Getter;

@Getter
public class RegisterRequest {
    private final String username;
    private final String password;
    private final String confirmPassword;
    private final String fullName;

    public RegisterRequest(String username, String password, String confirmPassword, String fullName) {
        this.username = username;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.fullName = fullName;
    }
}
