package com.mvucevski.lendingmanagement.api.payload;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoanDTO {

    private final String bookId;
    private final String userId;
    private final LocalDateTime dueDate;
    private final LocalDateTime returnedAt;

    public LoanDTO(String bookId, String userId, LocalDateTime dueDate, LocalDateTime returnedAt) {
        this.bookId = bookId;
        this.userId = userId;
        this.dueDate = dueDate;
        this.returnedAt = returnedAt;
    }
}
