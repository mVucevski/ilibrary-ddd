package com.mvucevski.lendingmanagement.port.payload;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AddLoanResponse {

    private final String userId;
    private final String userName;
    private final String bookId;
    private final LocalDateTime createdAt;
    private final LocalDateTime dueDate;

    public AddLoanResponse(String userId, String userName, String bookId, LocalDateTime createdAt, LocalDateTime dueDate) {
        this.userId = userId;
        this.userName = userName;
        this.bookId = bookId;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
    }
}
