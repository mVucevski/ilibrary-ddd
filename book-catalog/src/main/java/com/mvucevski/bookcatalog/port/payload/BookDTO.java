package com.mvucevski.bookcatalog.port.payload;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookDTO {

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
     private final double rating;

    public BookDTO(String id, String title, String author, String isbn, String description, String language, String genre, String coverUrl, int availableCopies, LocalDate publicationDate, double rating) {
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
        this.rating = rating;
    }
}
