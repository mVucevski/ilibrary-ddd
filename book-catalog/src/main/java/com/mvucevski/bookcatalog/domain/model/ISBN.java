package com.mvucevski.bookcatalog.domain.model;

import com.mvucevski.bookcatalog.exceptions.BookNotFoundException;
import com.mvucevski.bookcatalog.exceptions.InvalidISBNException;
import com.mvucevski.sharedkernel.domain.base.ValueObject;
import lombok.Value;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;

@Embeddable
public class ISBN implements ValueObject {

    private static final String VERY_SIMPLE_ISBN_CHECK = "^\\d{9}[\\d|X]$";

    private String isbn;

    public ISBN() {
    }

    public ISBN(@NonNull String isbn) {
        if (!isbn.trim().matches(VERY_SIMPLE_ISBN_CHECK)) {
            throw new InvalidISBNException("ISBN must be 10 characters!");
        }
        this.isbn = isbn.trim();
    }

    public String getIsbn() {
        return isbn;
    }
}