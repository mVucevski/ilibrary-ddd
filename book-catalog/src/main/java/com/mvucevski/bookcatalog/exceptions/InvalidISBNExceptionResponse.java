package com.mvucevski.bookcatalog.exceptions;

public class InvalidISBNExceptionResponse {
    String isbn;

    public InvalidISBNExceptionResponse(String isbn) {
        this.isbn = isbn;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
