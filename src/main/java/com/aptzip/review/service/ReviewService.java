package com.aptzip.review.service;

import com.aptzip.review.dto.response.UserReviewResponse;
import com.aptzip.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<UserReviewResponse> getUserReviewList(String userUuid) {
        return reviewRepository.findReviewsByUserUuid(userUuid);
    }
}
