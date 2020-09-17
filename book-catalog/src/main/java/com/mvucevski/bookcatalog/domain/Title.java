package com.mvucevski.bookcatalog.domain;

import lombok.Value;
import org.springframework.lang.NonNull;

@Value
class Title {

    String title;

    Title(@NonNull String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        this.title = title.trim();
    }

}