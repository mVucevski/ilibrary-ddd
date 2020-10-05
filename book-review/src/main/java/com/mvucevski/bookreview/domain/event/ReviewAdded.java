package com.mvucevski.bookreview.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mvucevski.bookreview.domain.model.BookId;
import com.mvucevski.sharedkernel.domain.base.DomainEvent;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.Objects;

public class ReviewAdded implements DomainEvent {

    @JsonProperty("bookId")
    private final String bookId;

    @JsonProperty("content")
    private final String content;

    @JsonProperty("rating")
    private final int rating;

    @JsonProperty("occurredOn")
    private final Instant occurredOn;

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
