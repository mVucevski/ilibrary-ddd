package com.mvucevski.bookcatalog.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public final ResponseEntity<Object> handleBookNotFoundException(BookNotFoundException ex, WebRequest request){
        BookNotFoundExceptionResponse exceptionResponse = new BookNotFoundExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleInvalidISBNException(InvalidISBNException ex, WebRequest request){
        InvalidISBNExceptionResponse exceptionResponse = new InvalidISBNExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler
    public final ResponseEntity<Object> handleInvalidTitleException(InvalidTitleException ex, WebRequest request) {
        InvalidTitleExceptionResponse exceptionResponse = new InvalidTitleExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler
    public final ResponseEntity<Object> handleInvalidAuthorException(InvalidAuthorException ex, WebRequest request){
        InvalidAuthorExceptionResponse exceptionResponse = new InvalidAuthorExceptionResponse(ex.getMessage());
        return new ResponseEntity(exceptionResponse, HttpStatus.BAD_REQUEST);
    }


}
