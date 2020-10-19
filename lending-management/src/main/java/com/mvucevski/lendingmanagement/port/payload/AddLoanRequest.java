package com.mvucevski.lendingmanagement.port.payload;

import lombok.Getter;

@Getter
public class AddLoanRequest {

    private String bookId;
    private String username;

    public AddLoanRequest(String bookId, String username) {
        this.bookId = bookId;
        this.username = username;
    }
}
