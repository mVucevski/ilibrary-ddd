package com.mvucevski.bookcatalog.exceptions;

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
