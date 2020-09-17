package com.mvucevski.bookcatalog.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mvucevski.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
public class Book extends AbstractEntity<BookId> {

    @EmbeddedId
    private BookId id;

    @Version
    private Long version;

    @Embedded
    private Title title;

    @Embedded
    private Author author;

    private String description;

    private Language language;

    private Genre genre;

    private int availableCopies;

    private String coverUrl;

    private LocalDate publicationDate;

    @Column(columnDefinition = "TIMESTAMP", name = "created_at", updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TIMESTAMP", name = "updated_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
