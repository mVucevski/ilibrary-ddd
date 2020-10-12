package com.mvucevski.lendingmanagement.api.payload;

import lombok.Getter;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;

@Getter
public class LoanDTO {

    private final String bookId;
    private final String userId;
    private final LocalDateTime dueDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime returnedAt;
    private final int fee;

    public LoanDTO(String bookId, String userId, LocalDateTime dueDate, LocalDateTime createdAt, LocalDateTime returnedAt, int fee) {
        this.bookId = bookId;
        this.userId = userId;
        this.dueDate = dueDate;
        this.createdAt = createdAt;
        this.returnedAt = returnedAt;
        this.fee = fee;
    }
}
