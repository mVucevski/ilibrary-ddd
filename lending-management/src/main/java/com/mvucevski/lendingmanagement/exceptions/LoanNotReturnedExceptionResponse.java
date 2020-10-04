package com.mvucevski.lendingmanagement.exceptions;

public class LoanNotReturnedExceptionResponse {
    private String loanNotReturned;

    public LoanNotReturnedExceptionResponse(String loanNotReturned) {
        this.loanNotReturned = loanNotReturned;
    }

    public String getLoanNotReturned() {
        return loanNotReturned;
    }

    public void setLoanNotReturned(String loanNotReturned) {
        this.loanNotReturned = loanNotReturned;
    }
}
