package com.mvucevski.lendingmanagement.api.payload;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AddReservationResponse {

    private String bookId;
    private String userId;
    private LocalDateTime endsAt;

    public AddReservationResponse(String bookId, LocalDateTime endsAt) {
        this.bookId = bookId;
        this.endsAt = endsAt;
    }
}
