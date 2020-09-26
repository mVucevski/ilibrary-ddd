package com.mvucevski.lendingmanagement.api.payload;

import lombok.Getter;

@Getter
public class AddLoanRequest {

    private String bookId;
    private String userId;

    public AddLoanRequest(String bookId, String userId) {
        this.bookId = bookId;
        this.userId = userId;
    }
}
