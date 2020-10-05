package com.mvucevski.bookreview.service;

import com.mvucevski.bookreview.domain.event.ReviewAdded;
import com.mvucevski.bookreview.domain.event.ReviewEdited;
import com.mvucevski.bookreview.domain.model.BookId;
import com.mvucevski.bookreview.domain.model.Review;
import com.mvucevski.bookreview.domain.model.ReviewId;
import com.mvucevski.bookreview.domain.model.UserId;
import com.mvucevski.bookreview.repository.ReviewsRepository;
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


    public ReviewsService(@Qualifier("dbReviewsRepository") ReviewsRepository repository,
                          RabbitTemplate rabbitTemplate) {
        this.repository = repository;
        this.rabbitTemplate = rabbitTemplate;
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
                System.out.println("REVIEWED EDIT");
                rabbitTemplate.convertAndSend(EXCHANGE_NAME, BOOK_REVIEW_EDITED_ROUTING_KEY, new ReviewEdited(bookId.getId(), content, oldContent, rating, oldRating, Instant.now()));
            }else{
                System.out.println("REVIEWED ADDED");
                rabbitTemplate.convertAndSend(EXCHANGE_NAME, BOOK_REVIEW_ADDED_ROUTING_KEY, new ReviewAdded(bookId.getId(), content, rating, Instant.now()));
            }

            //TODO Logger Save Review
            return savedReview;
//        }catch (Exception ex){
//            System.out.println("Something went from with saving review");
//            //TODO Error Logger
//            System.out.println(ex.getMessage());
//            ex.getStackTrace();
//            throw new RuntimeException("Something went from with saving review");
//        }

    }

    public Review saveReview(Review review){
        return repository.saveReview(review);
    }

    public List<Review> getAllReviewsByBookId(BookId bookId){
        return repository.getAllReviewsByBookId(bookId);
    }
}
