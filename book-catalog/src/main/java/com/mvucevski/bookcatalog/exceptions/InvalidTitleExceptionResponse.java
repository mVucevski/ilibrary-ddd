package com.mvucevski.bookcatalog.exceptions;

public class InvalidTitleExceptionResponse {
    String title;

    public InvalidTitleExceptionResponse(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
