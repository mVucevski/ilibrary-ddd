package com.mvucevski.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UsernameDoesntExistException extends RuntimeException{

    public UsernameDoesntExistException(String msg){
        super(msg);
    }
}
