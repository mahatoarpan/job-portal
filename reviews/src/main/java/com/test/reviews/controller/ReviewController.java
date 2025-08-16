package com.test.reviews.controller;

import com.test.reviews.entity.Review;
import com.test.reviews.messaging.ReviewMessageProducer;
import com.test.reviews.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMessageProducer reviewMessageProducer;

    public ReviewController(ReviewService reviewService, ReviewMessageProducer reviewMessageProducer) {
        this.reviewService = reviewService;
        this.reviewMessageProducer = reviewMessageProducer;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews(@RequestParam("companyId") Long companyId) {
        List<Review> companies = reviewService.getAllReviews(companyId);
        return new ResponseEntity<>(companies, HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<Review> getReviewById( @PathVariable("reviewId") Long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        if (review != null) {
            return new ResponseEntity<>(review, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReviewById( @PathVariable("reviewId") Long reviewId,
                                                   @RequestBody Review updatedReview) {
        boolean isReviewUpdated = reviewService.updateReview( reviewId, updatedReview);
        if (isReviewUpdated) {
            return new ResponseEntity<>("Company Updated Successfully", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<String> createReview(@RequestParam("companyId") Long companyId, @RequestBody Review review) {
        boolean isReviewAdded = reviewService.addReview(companyId, review);
        if (isReviewAdded) {
            reviewMessageProducer.sendMessage(review);
            return new ResponseEntity<>("Review Added Successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteCompanyById(@PathVariable("reviewId") Long companyId, @PathVariable Long reviewId) {
        boolean isReviewDeleted = reviewService.deleteReview(reviewId);
        if (isReviewDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/averageRating")
    public Double getAverageRating(@RequestParam("companyId") Long companyId) {
        List<Review> reviews = reviewService.getAllReviews(companyId);

        return reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
    }
}
