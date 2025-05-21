package com.aptzip.review.service;

import com.aptzip.review.dto.request.DongReviewListRequest;
import com.aptzip.review.dto.response.DongReviewListResponse;
import com.aptzip.review.dto.response.UserReviewListResponse;
import com.aptzip.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public List<UserReviewListResponse> getUserReviewList(String userUuid) {
        return reviewRepository.findReviewsByUserUuid(userUuid);
    }

    public List<DongReviewListResponse> getDongReviewList(DongReviewListRequest dongReviewListRequest) {
        return reviewRepository.findReviewsByDongCode(dongReviewListRequest.dongCode());
    }
}
