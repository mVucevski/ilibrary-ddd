package com.mvucevski.lendingmanagement.domain;

import com.mvucevski.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
public class Reservation extends AbstractEntity<ReservationId> {

    @Version
    private Long version;

    private BookId bookId;

    private UserId userId;

    @Column(columnDefinition = "TIMESTAMP", name = "ends_at")
    private final LocalDateTime endsAt = LocalDateTime.now().plusDays(1);

    @Column(columnDefinition = "TIMESTAMP", name = "created_at", updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TIMESTAMP", name = "updated_at")
    private LocalDateTime updatedAt;

    public Reservation() {}

    public Reservation(BookId bookId, UserId userId) {
        super(ReservationId.randomId(ReservationId.class));
        this.bookId = bookId;
        this.userId = userId;
    }

    public boolean isExpired(){
        LocalDateTime currentDate = LocalDateTime.now();
        return currentDate.isAfter(endsAt);
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
