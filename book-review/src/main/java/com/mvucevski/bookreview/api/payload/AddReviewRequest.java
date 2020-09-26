package com.mvucevski.bookreview.api.payload;

import lombok.Getter;
import org.springframework.lang.NonNull;

@Getter
public class AddReviewRequest {

    private String userId;
    private String bookId;
    private int rating;
    private String content;

    public AddReviewRequest(String userId, String bookId, int rating, String content) {
        this.userId = userId;
        this.bookId = bookId;
        this.rating = rating;
        this.content = content;
    }
}
