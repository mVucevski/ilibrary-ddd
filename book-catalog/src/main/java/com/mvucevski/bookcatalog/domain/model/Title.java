package com.mvucevski.bookcatalog.domain.model;

import com.mvucevski.bookcatalog.exceptions.InvalidTitleException;
import com.mvucevski.sharedkernel.domain.base.ValueObject;
import lombok.Value;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;

@Embeddable
public class Title implements ValueObject {

    private final String title;

    public Title() {
        title = "";
    }

    public Title(@NonNull String title) {
        if (title.isEmpty()) {
            throw new InvalidTitleException("Title cannot be empty");
        }
        this.title = title.trim();
    }

    public String getTitle() {
        return title;
    }
}