package com.mvucevski.lendingmanagement.exceptions;

public class ReservationsLoansLimitExceptionResponse {
    private String limitExceeded;

    public ReservationsLoansLimitExceptionResponse(String limitExceeded) {
        this.limitExceeded = limitExceeded;
    }

    public String getLimitExceeded() {
        return limitExceeded;
    }

    public void setLimitExceeded(String limitExceeded) {
        this.limitExceeded = limitExceeded;
    }
}
