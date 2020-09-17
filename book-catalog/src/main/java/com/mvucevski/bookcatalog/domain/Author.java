package com.mvucevski.bookcatalog.domain;

import lombok.Value;
import org.springframework.lang.NonNull;

@Value
class Author {

    String name;

    Author(@NonNull String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Author cannot be empty");
        }
        this.name = name.trim();
    }
}