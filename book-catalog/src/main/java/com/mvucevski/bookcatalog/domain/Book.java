package com.mvucevski.bookcatalog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mvucevski.sharedkernel.domain.base.AbstractEntity;
import com.mvucevski.sharedkernel.domain.base.DomainObjectId;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
public class Book extends AbstractEntity<BookId> {

    @Version
    private Long version;

    @Embedded
    private Title title;

    @Embedded
    private Author author;

    private String description;

    @Enumerated(EnumType.STRING)
    private Language language;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private int availableCopies = 0;

    private String coverUrl;

    private LocalDate publicationDate;

    @Column(columnDefinition = "TIMESTAMP", name = "created_at", updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TIMESTAMP", name = "updated_at")
    private LocalDateTime updatedAt;


    public Book(Title title, Author author, String description, Language language, Genre genre, int availableCopies,
                String coverUrl, LocalDate publicationDate) {
        super(DomainObjectId.randomId(BookId.class));
        this.title = title;
        this.author = author;
        this.description = description;
        this.language = language;
        this.genre = genre;
        this.availableCopies = availableCopies;
        this.coverUrl = coverUrl;
        this.publicationDate = publicationDate;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public void addCopies(int bookCopies){
        if(bookCopies <= 0){
            throw new IllegalArgumentException("Book copies must be positive number");
        }
        this.availableCopies += bookCopies;
    }

    public void subtractCopies(int bookCopies){
        if(bookCopies > this.availableCopies){
            throw new IllegalArgumentException("Unsupported number of copies");
        }
        this.availableCopies -= bookCopies;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
}
