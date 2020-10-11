package com.mvucevski.lendingmanagement.domain;

import com.mvucevski.sharedkernel.domain.base.AbstractEntity;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

@Entity
@Getter
public class Loan extends AbstractEntity<LoanId> {

    @Version
    private Long version;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "book_id", nullable = false)))
    private BookId bookId;

    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "id", column = @Column(name = "user_id", nullable = false)))
    private UserId userId;

    private int fee = 0;

    @Column(columnDefinition = "TIMESTAMP", name = "due_date")
    private final LocalDateTime dueDate = LocalDateTime.now().plusWeeks(2);

    @Column(columnDefinition = "TIMESTAMP", name = "created_at", updatable = false)
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Column(columnDefinition = "TIMESTAMP", name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(columnDefinition = "TIMESTAMP", name = "returned_at")
    private LocalDateTime returnedAt;

    public Loan() {
    }

    public Loan(BookId bookId, UserId userId) {
        super(LoanId.randomId(LoanId.class));
        this.bookId = bookId;
        this.userId = userId;
    }

    public Integer getFee() {
        if(returnedAt == null) {
            calculateFee();
        }
        return fee;
    }

    private void calculateFee(){
        LocalDateTime dateNow = LocalDateTime.now();

        if (dateNow.isAfter(dueDate)) {
            long daysBetween = DAYS.between(dueDate, dateNow);

            fee = (int) (daysBetween / 7) + 1;
        }
    }

    public void setBookId(BookId bookId) {
        this.bookId = bookId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public void setReturnedAt(LocalDateTime returnedAt) {
        this.returnedAt = returnedAt;
    }

    @PreUpdate
    protected void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
