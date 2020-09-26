package com.mvucevski.bookreview.api.payload;

import lombok.Getter;

@Getter
public class AddReviewResponse {
    private int rating;
    private String content;

    public AddReviewResponse(int rating, String content) {
        this.rating = rating;
        this.content = content;
    }
}
