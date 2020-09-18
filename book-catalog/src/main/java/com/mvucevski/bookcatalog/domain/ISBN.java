package com.mvucevski.bookcatalog.domain;

import com.mvucevski.sharedkernel.domain.base.ValueObject;
import lombok.Value;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;

@Embeddable
@Value
public class ISBN implements ValueObject {

    private static final String VERY_SIMPLE_ISBN_CHECK = "^\\d{9}[\\d|X]$";

    String isbn;

    public ISBN(@NonNull String isbn) {
        if (!isbn.trim().matches(VERY_SIMPLE_ISBN_CHECK)) {
            throw new IllegalArgumentException("Wrong ISBN!");
        }
        this.isbn = isbn.trim();
    }

}