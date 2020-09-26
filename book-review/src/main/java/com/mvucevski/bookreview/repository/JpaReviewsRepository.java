package com.mvucevski.bookreview.repository;

import com.mvucevski.bookreview.domain.Review;
import com.mvucevski.bookreview.domain.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaReviewsRepository extends JpaRepository<Review, ReviewId> {
}
