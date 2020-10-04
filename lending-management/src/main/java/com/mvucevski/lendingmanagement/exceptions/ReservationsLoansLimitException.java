package com.mvucevski.lendingmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ReservationsLoansLimitException extends RuntimeException{
    public ReservationsLoansLimitException(String msg){
        super(msg);
    }
}
