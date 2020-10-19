package com.mvucevski.bookcatalog.domain.model;

import com.mvucevski.bookcatalog.exceptions.InvalidAuthorException;
import com.mvucevski.sharedkernel.domain.base.ValueObject;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.lang.NonNull;

import javax.persistence.Embeddable;

@Embeddable
public class Author implements ValueObject {

    private final String name;

    public Author(){
        name = "";
    }

    public Author(@NonNull String name) {
        if (name.isEmpty()) {
            throw new InvalidAuthorException("Author cannot be empty");
        }
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }
}