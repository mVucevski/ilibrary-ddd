package com.mvucevski.bookcatalog.api.payload;

import lombok.Getter;

@Getter
public class BookListItem {

    private final String id;
    private final String title;
    private final String author;
    private final String coverUrl;
    private final int availableCopies;
    private final double rating;


    public BookListItem(String id, String title, String author, String coverUrl, int availableCopies, double rating) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.coverUrl = coverUrl;
        this.availableCopies = availableCopies;
        this.rating = rating;
    }
}
