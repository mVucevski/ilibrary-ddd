package com.mvucevski.usermanagement.exception;

public class UnauthorizedAccessResponse {

    private String unauthorizedAccess;

    public UnauthorizedAccessResponse(String unauthorizedAccess) {
        this.unauthorizedAccess = unauthorizedAccess;
    }

    public String getUnauthorizedAccess() {
        return unauthorizedAccess;
    }

    public void setUnauthorizedAccess(String unauthorizedAccess) {
        this.unauthorizedAccess = unauthorizedAccess;
    }
}
