package com.mvucevski.bookcatalog.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mvucevski.sharedkernel.domain.base.DomainEvent;
import lombok.Value;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.Objects;

public class ReviewAdded implements DomainEvent {

    @JsonProperty("bookId")
    private String bookId;

    @JsonProperty("content")
    private String content;

    @JsonProperty("rating")
    private int rating;

    @JsonProperty("occurredOn")
    private Instant occurredOn;


    @JsonCreator
    public ReviewAdded(@JsonProperty("bookId") @NonNull String bookId,
                       @JsonProperty("content") @NonNull String content,
                       @JsonProperty("rating") @NonNull int rating,
                       @JsonProperty("occurredOn") @NonNull Instant occurredOn) {
        this.bookId = Objects.requireNonNull(bookId, "bookId must not be null");
        this.content = Objects.requireNonNull(content, "content must not be null");
        this.rating = Objects.requireNonNull(rating, "rating must not be null");
        this.occurredOn = Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }

    public ReviewAdded() {
    }

    public String bookId(){return bookId;}

    public String content() {
        return content;
    }

    public int rating() {
        return rating;
    }

    @NonNull
    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}
