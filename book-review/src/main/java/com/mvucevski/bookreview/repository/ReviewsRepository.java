package com.mvucevski.bookreview.repository;

import com.mvucevski.bookreview.domain.model.BookId;
import com.mvucevski.bookreview.domain.model.Review;
import com.mvucevski.bookreview.domain.model.ReviewId;
import com.mvucevski.bookreview.domain.model.UserId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewsRepository {

    List<Review> getAllReviews();

    Optional<Review> getReviewById(ReviewId reviewId);

    Review saveReview(Review review);

    Boolean deleteReview(ReviewId reviewId);

    List<Review> getAllReviewsByBookId(BookId bookId);

    Optional<Review> findReviewByBookIdAndUserId(BookId bookId, UserId userId);
}
