package com.mvucevski.usermanagement.exception;


public class UserMembershipExceptionResponse {
    private String userMembershipException;

    public UserMembershipExceptionResponse(String userMembershipException) {
        this.userMembershipException = userMembershipException;
    }

    public String getUserMembershipException() {
        return userMembershipException;
    }

    public void setUserMembershipException(String userMembershipException) {
        this.userMembershipException = userMembershipException;
    }
}
