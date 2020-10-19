package com.mvucevski.bookreview.domain.repository;

import com.mvucevski.bookreview.domain.model.BookId;
import com.mvucevski.bookreview.domain.model.Review;
import com.mvucevski.bookreview.domain.model.ReviewId;
import com.mvucevski.bookreview.domain.model.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaReviewsRepository extends JpaRepository<Review, ReviewId> {

    List<Review> findReviewsByBookId(BookId id);

    Optional<Review> findReviewByBookIdAndUserId(BookId bookId, UserId userId);
}
