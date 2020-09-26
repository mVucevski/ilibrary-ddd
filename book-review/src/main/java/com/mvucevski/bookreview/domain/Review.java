package com.mvucevski.bookreview.domain;

import com.mvucevski.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Review extends AbstractEntity<ReviewId> {

    @Version
    private Long version;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "book_id", nullable = false)))
    private BookId bookId;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false)))
    private UserId userId;

    private int rating;

    private String content;

    @Column(columnDefinition = "TIMESTAMP", name = "created_at", updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TIMESTAMP", name = "updated_at")
    private LocalDateTime updatedAt;

    public Review(BookId bookId, UserId userId, int rating, String content) {
        super(ReviewId.randomId(ReviewId.class));
        this.bookId = bookId;
        this.userId = userId;
        this.rating = rating;
        this.content = content;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setBookId(BookId bookId) {
        this.bookId = bookId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
