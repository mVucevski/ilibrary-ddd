package com.mvucevski.lendingmanagement.port.payload;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AddReservationResponse {

    private String bookId;
    private LocalDateTime endsAt;

    public AddReservationResponse(String bookId, LocalDateTime endsAt) {
        this.bookId = bookId;
        this.endsAt = endsAt;
    }
}
