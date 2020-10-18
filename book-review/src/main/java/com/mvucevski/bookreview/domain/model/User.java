package com.mvucevski.bookreview.domain.model;

import lombok.Getter;

@Getter
public class User {

    private UserId id;

    private String username;

    private String fullName;

    private boolean isMembershipExpired;

    private String role;

    public User() {
    }

    public User(UserId id, String username, String fullName, boolean isMembershipExpired, String role) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.isMembershipExpired = isMembershipExpired;
        this.role = role;
    }

}
