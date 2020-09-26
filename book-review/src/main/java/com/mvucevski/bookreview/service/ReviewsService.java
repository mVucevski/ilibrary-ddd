package com.mvucevski.bookreview.service;

import com.mvucevski.bookreview.domain.BookId;
import com.mvucevski.bookreview.domain.Review;
import com.mvucevski.bookreview.domain.ReviewId;
import com.mvucevski.bookreview.domain.UserId;
import com.mvucevski.bookreview.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewsService {

    private final ReviewsRepository repository;

    public ReviewsService(@Qualifier("dbReviewsRepository") ReviewsRepository repository) {
        this.repository = repository;
    }

    public List<Review> getAllReviews(){
        return repository.getAllReviews();
    }

    public Optional<Review> getReviewById(ReviewId reviewId){
        return repository.getReviewById(reviewId);
    }

    public Review creatReview(UserId userId, BookId bookId, int rating, String content){
        return addReview(new Review(bookId, userId, rating, content));
    }

    public Review addReview(Review review){
        return repository.saveReview(review);
    }

    public List<Review> getAllReviewsByBookId(BookId bookId){
        return repository.getAllReviewsByBookId(bookId);
    }
}
