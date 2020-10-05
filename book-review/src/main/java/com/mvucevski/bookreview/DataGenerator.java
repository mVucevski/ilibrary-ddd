package com.mvucevski.bookreview;

import com.mvucevski.bookreview.domain.model.BookId;
import com.mvucevski.bookreview.domain.model.Review;
import com.mvucevski.bookreview.domain.model.UserId;
import com.mvucevski.bookreview.repository.DbReviewsRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

@Component
public class DataGenerator {

    private final DbReviewsRepository repository;

    public DataGenerator(DbReviewsRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    @Transactional
    public void generateData(){

        if(repository.getAllReviews().isEmpty()){
            repository.saveReview(new Review(new BookId("7c997e8b-ea90-4be9-8487-ca35251af2ec"), new UserId("0"), 3, "This is comment"));
            repository.saveReview(new Review(new BookId("7c997e8b-ea90-4be9-8487-ca35251af2ec"), new UserId("1"), 4, "This is comment"));
        }

    }
}
