package com.mvucevski.lendingmanagement.exceptions;

public class BookAvailableCopiesExceptionResponse {

    private String availableCopies;

    public BookAvailableCopiesExceptionResponse(String availableCopies) {
        this.availableCopies = availableCopies;
    }

    public String getAvailableCopies() {
        return availableCopies;
    }

    public void setAvailableCopies(String availableCopies) {
        this.availableCopies = availableCopies;
    }
}
