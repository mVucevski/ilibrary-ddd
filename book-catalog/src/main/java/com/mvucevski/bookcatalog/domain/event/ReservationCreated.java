package com.mvucevski.bookcatalog.domain.event;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mvucevski.sharedkernel.domain.base.DomainEvent;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.Objects;

public class ReservationCreated implements DomainEvent {

    @JsonProperty("bookId")
    private String bookId;

    @JsonProperty("occurredOn")
    private Instant occurredOn;

    @JsonCreator
    public ReservationCreated(@JsonProperty("bookId") @NonNull String bookId,
                              @JsonProperty("occurredOn") @NonNull Instant occurredOn) {
        this.bookId = Objects.requireNonNull(bookId, "bookId must not be null");
        this.occurredOn = Objects.requireNonNull(occurredOn, "occurredOn must not be null");
    }

    public ReservationCreated() {
    }

    public String bookId(){return bookId;}

    @NonNull
    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}
