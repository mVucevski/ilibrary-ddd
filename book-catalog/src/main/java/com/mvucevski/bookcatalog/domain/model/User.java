package com.mvucevski.bookcatalog.domain.model;


import lombok.Getter;


@Getter
public class User {

    private UserId id;

    private String username;

    private String fullName;

    private Boolean isMembershipExpired;

    private String role;

    public User() {
    }

    public User(UserId userId, String username, String fullName, Boolean isMembershipExpired, String role) {
        this.id = userId;
        this.username = username;
        this.fullName = fullName;
        this.isMembershipExpired = isMembershipExpired;
        this.role = role;
    }



}
