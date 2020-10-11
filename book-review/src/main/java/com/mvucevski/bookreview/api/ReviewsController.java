package com.mvucevski.bookreview.api;

import com.mvucevski.bookreview.api.payload.AddReviewRequest;
import com.mvucevski.bookreview.api.payload.AddReviewResponse;
import com.mvucevski.bookreview.api.payload.ReviewDTO;
import com.mvucevski.bookreview.domain.model.BookId;
import com.mvucevski.bookreview.domain.model.Review;
import com.mvucevski.bookreview.domain.model.User;
import com.mvucevski.bookreview.service.ReviewsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api/reviews")
public class ReviewsController {

    private final ReviewsService service;

    public ReviewsController(ReviewsService service) {
        this.service = service;
    }

    @GetMapping("/{bookId}")
    public List<ReviewDTO> getReviewsByBookId(@PathVariable String bookId){
        return service.getAllReviewsByBookId(new BookId(bookId)).stream().map(e->
                new ReviewDTO(e.getUserId().getId(), e.getContent(), e.getRating(), e.getCreatedAt().toLocalDate())).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> addReview(@RequestBody AddReviewRequest request, @AuthenticationPrincipal User user){//Principal principal){

        if(user==null){
            return new ResponseEntity<String>("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }

        Review review = service.creatReview(user.getUserId(), new BookId(request.getBookId()),
                    request.getRating(), request.getContent());


        return new ResponseEntity<AddReviewResponse>(
                new AddReviewResponse(
                        review.id().getId(),
                        review.getRating(),
                        review.getContent()),
                HttpStatus.OK);
    }


}
