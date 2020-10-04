package com.mvucevski.lendingmanagement.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BookAvailableCopiesException extends RuntimeException {

    public BookAvailableCopiesException(String message) {
        super(message);
    }
}
