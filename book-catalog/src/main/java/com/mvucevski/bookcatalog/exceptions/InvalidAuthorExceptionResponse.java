package com.mvucevski.bookcatalog.exceptions;

public class InvalidAuthorExceptionResponse {
    private String author;

    public InvalidAuthorExceptionResponse(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
