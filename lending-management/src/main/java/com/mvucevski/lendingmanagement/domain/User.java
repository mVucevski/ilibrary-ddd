package com.mvucevski.lendingmanagement.domain;


import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class User {

    private UserId userId;

    private String username;

    private String fullName;

    private boolean isMembershipExpired;

    private String role;

    public User() {
    }

    public User(UserId userId, String username, String fullName, boolean isMembershipExpired, String role) {
        this.userId = userId;
        this.username = username;
        this.fullName = fullName;
        this.isMembershipExpired = isMembershipExpired;
        this.role = role;
    }



}
