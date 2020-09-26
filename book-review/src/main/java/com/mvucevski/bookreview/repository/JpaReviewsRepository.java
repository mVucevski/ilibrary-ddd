package com.mvucevski.bookreview.repository;

import com.mvucevski.bookreview.domain.BookId;
import com.mvucevski.bookreview.domain.Review;
import com.mvucevski.bookreview.domain.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaReviewsRepository extends JpaRepository<Review, ReviewId> {

    List<Review> findReviewsByBookId(BookId id);
}
