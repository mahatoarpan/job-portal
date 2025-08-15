package com.test.reviews.service;

import com.test.reviews.entity.Review;

import java.util.List;

public interface ReviewService {

    List<Review> getAllReviews(Long companyId);

    boolean updateReview( Long reviewId, Review updatedReview);

    boolean addReview(Long companyId, Review review);

    boolean deleteReview(Long reviewId);

    Review getReviewById( Long reviewId);
}
