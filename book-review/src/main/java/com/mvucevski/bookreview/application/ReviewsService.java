package com.mvucevski.bookreview.application;

import com.mvucevski.bookreview.domain.event.ReviewAdded;
import com.mvucevski.bookreview.domain.event.ReviewEdited;
import com.mvucevski.bookreview.domain.model.BookId;
import com.mvucevski.bookreview.domain.model.Review;
import com.mvucevski.bookreview.domain.model.ReviewId;
import com.mvucevski.bookreview.domain.model.UserId;
import com.mvucevski.bookreview.domain.repository.ReviewsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static com.mvucevski.bookreview.utils.AppConstants.*;

@Service
public class ReviewsService {

    private final ReviewsRepository repository;
    private final RabbitTemplate rabbitTemplate;
    private final Logger logger;

    public ReviewsService(@Qualifier("dbReviewsRepository") ReviewsRepository repository,
                          RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
        logger = LoggerFactory.getLogger(ReviewsService.class);
    }

    public List<Review> getAllReviews(){
        return repository.getAllReviews();
    }

    public Optional<Review> getReviewById(ReviewId reviewId){
        return repository.getReviewById(reviewId);
    }

    public Review creatReview(UserId userId, BookId bookId, int rating, String content){

        //try{
            String oldContent = "";
            int oldRating = -1;


            Optional<Review> reviewOpt = repository.findReviewByBookIdAndUserId(bookId, userId);
            Review savedReview;

            if(reviewOpt.isPresent()){
                Review review = reviewOpt.get();

                oldRating = review.getRating();
                oldContent = review.getContent();
                review.setContent(content);
                review.setRating(rating);

                savedReview = saveReview(review);
            }else{
                savedReview = saveReview(new Review(bookId, userId, rating, content));
            }

            if(oldRating!=-1){
                logger.info("Edited review with id: " + savedReview.getId().getId());
                rabbitTemplate.convertAndSend(EXCHANGE_NAME, BOOK_REVIEW_EDITED_ROUTING_KEY, new ReviewEdited(bookId.getId(), content, oldContent, rating, oldRating, Instant.now()));
            }else{
                logger.info("Added review with id: " + savedReview.getId().getId());
                rabbitTemplate.convertAndSend(EXCHANGE_NAME, BOOK_REVIEW_ADDED_ROUTING_KEY, new ReviewAdded(bookId.getId(), content, rating, Instant.now()));
            }

            return savedReview;
    }

    public Review saveReview(Review review){
        return repository.saveReview(review);
    }

    public List<Review> getAllReviewsByBookId(BookId bookId){
        return repository.getAllReviewsByBookId(bookId);
    }
}
