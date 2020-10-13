package com.mvucevski.bookcatalog.api.payload;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateBookRequest {

    private final String id;
    private final String title;
    private final String author;
    private final String isbn;
    private final String description;
    private final String language;
    private final String genre;
    private final String coverUrl;
    private final int availableCopies;
    private final LocalDate publicationDate;

    public CreateBookRequest(String id, String title, String author, String isbn, String description, String language, String genre, String coverUrl, int availableCopies, LocalDate publicationDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.description = description;
        this.language = language;
        this.genre = genre;
        this.coverUrl = coverUrl;
        this.availableCopies = availableCopies;
        this.publicationDate = publicationDate;
    }
}
