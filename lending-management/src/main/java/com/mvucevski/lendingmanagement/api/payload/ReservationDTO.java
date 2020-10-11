package com.mvucevski.lendingmanagement.api.payload;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationDTO {

    private final String bookId;
    private final String userId;
    private final LocalDateTime endsAt;

    public ReservationDTO(String bookId, String userId, LocalDateTime endsAt) {
        this.bookId = bookId;
        this.userId = userId;
        this.endsAt = endsAt;
    }
}
