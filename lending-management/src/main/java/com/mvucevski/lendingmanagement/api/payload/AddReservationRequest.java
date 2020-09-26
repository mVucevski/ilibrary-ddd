package com.mvucevski.lendingmanagement.api.payload;

import lombok.Getter;

@Getter
public class AddReservationRequest {

    private String userId;
    private String bookId;

    public AddReservationRequest(String userId, String bookId) {
        this.userId = userId;
        this.bookId = bookId;
    }
}
