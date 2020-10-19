package com.mvucevski.bookreview.port.payload;

import lombok.Getter;

@Getter
public class AddReviewRequest {

    private String bookId;
    private int rating;
    private String content;

    public AddReviewRequest(String bookId, int rating, String content) {
        this.bookId = bookId;
        this.rating = rating;
        this.content = content;
    }
}
