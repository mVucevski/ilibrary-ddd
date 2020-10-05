package com.mvucevski.bookcatalog.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mvucevski.sharedkernel.domain.base.DomainEvent;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.Objects;



public class ReviewEdited implements DomainEvent {

    @JsonProperty("bookId")
    private String bookId;

    @JsonProperty("newContent")
    private String newContent;

    @JsonProperty("oldContent")
    private String oldContent;

    @JsonProperty("newRating")
    private int newRating;

    @JsonProperty("oldRating")
    private int oldRating;

    @JsonProperty("occurredOn")
    private Instant occurredOn;

    public ReviewEdited() {
    }

    @JsonCreator
    public ReviewEdited(@JsonProperty("bookId") @NonNull String bookId,
                        @JsonProperty("newContent") @NonNull String newContent,
                        @JsonProperty("oldContent") @NonNull String oldContent,
                        @JsonProperty("newRating") int newRating,
                        @JsonProperty("oldRating") int oldRating,
                        @JsonProperty("occurredOn") @NonNull Instant occurredOn) {
        this.bookId = Objects.requireNonNull(bookId, "bookId must not be null");
        this.newContent = Objects.requireNonNull(newContent, "new content must not be null");
        this.oldContent = Objects.requireNonNull(oldContent, "old content must not be null");
        this.newRating = newRating;
        this.oldRating = oldRating;
        this.occurredOn = Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }

    public String bookId(){return bookId;}

    public String newContent() {
        return newContent;
    }

    public int newRating() {
        return newRating;
    }

    public int oldRating() {
        return oldRating;
    }

    public String oldContent(){return oldContent;}

    @NonNull
    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}