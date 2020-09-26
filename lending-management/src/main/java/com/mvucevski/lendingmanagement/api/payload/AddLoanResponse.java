package com.mvucevski.lendingmanagement.api.payload;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AddLoanResponse {

    private String userId;
    private String bookId;
    private LocalDateTime createdAt;
    private LocalDateTime dueDate;

    public AddLoanResponse(String userId, String bookId, LocalDateTime createdAt, LocalDateTime dueDate) {
        this.userId = userId;
        this.bookId = bookId;
        this.createdAt = createdAt;
        this.dueDate = dueDate;
    }
}
