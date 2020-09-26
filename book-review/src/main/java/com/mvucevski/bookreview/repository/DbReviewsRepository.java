package com.mvucevski.bookreview.repository;

import com.mvucevski.bookreview.domain.Review;
import com.mvucevski.bookreview.domain.ReviewId;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DbReviewsRepository implements ReviewsRepository{

    private final JpaReviewsRepository repository;

    public DbReviewsRepository(JpaReviewsRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Review> getAllReviews() {
        return repository.findAll();
    }

    @Override
    public Optional<Review> getReviewById(ReviewId reviewId) {
        return repository.findById(reviewId);
    }

    @Override
    public Review saveReview(Review review) {
        return repository.save(review);
    }

    @Override
    public Boolean deleteReview(ReviewId reviewId) {
        repository.deleteById(reviewId);
        return true;
    }
}
