package com.mvucevski.lendingmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoanNotReturnedException extends RuntimeException {
    public LoanNotReturnedException(String message){
        super(message);
    }
}
