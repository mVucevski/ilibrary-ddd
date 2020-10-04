package com.mvucevski.lendingmanagement.exceptions;

public class BookNotFoundExceptionResponse {
    String bookNotFound;

    public BookNotFoundExceptionResponse(String bookNotFound) {
        this.bookNotFound = bookNotFound;
    }

    public String getBookNotFound() {
        return bookNotFound;
    }

    public void setBookNotFound(String bookNotFound) {
        this.bookNotFound = bookNotFound;
    }
}
