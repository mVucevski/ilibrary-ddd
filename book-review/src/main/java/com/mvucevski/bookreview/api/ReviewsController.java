package com.mvucevski.bookreview.api;

import com.mvucevski.bookreview.api.payload.AddReviewRequest;
import com.mvucevski.bookreview.api.payload.AddReviewResponse;
import com.mvucevski.bookreview.domain.BookId;
import com.mvucevski.bookreview.domain.Review;
import com.mvucevski.bookreview.domain.UserId;
import com.mvucevski.bookreview.service.ReviewsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {

    private final ReviewsService service;

    public ReviewsController(ReviewsService service) {
        this.service = service;
    }

    @GetMapping("/{bookId}")
    public List<Review> getReviewsByBookId(@PathVariable String bookId){
        return service.getAllReviewsByBookId(new BookId(bookId));
    }

    @PostMapping
    public AddReviewResponse addReview(@RequestBody AddReviewRequest request){

        Review review = service.creatReview(new UserId(request.getUserId()), new BookId(request.getBookId()),
                request.getRating(), request.getContent());

        return new AddReviewResponse(review.getRating(), review.getContent());
    }

}
