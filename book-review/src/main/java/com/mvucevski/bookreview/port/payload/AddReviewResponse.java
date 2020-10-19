package com.mvucevski.bookreview.port.payload;

import lombok.Getter;

@Getter
public class AddReviewResponse {
    private String reviewId;
    private int rating;
    private String content;

    public AddReviewResponse(String reviewId, int rating, String content) {
        this.reviewId = reviewId;
        this.rating = rating;
        this.content = content;
    }
}
