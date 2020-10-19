package com.mvucevski.bookreview.port.payload;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ReviewDTO {
    private final String userId;
    private final String content;
    private final int rating;
    private final LocalDate createdAt;

    public ReviewDTO(String userId, String content, int rating, LocalDate createdAt) {
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.createdAt = createdAt;
    }
}
