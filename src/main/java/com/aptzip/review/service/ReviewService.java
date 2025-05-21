package com.aptzip.review.service;

import com.aptzip.dealMap.entity.DongCode;
import com.aptzip.review.dto.request.RegistReviewRequest;
import com.aptzip.review.dto.response.DongReviewListResponse;
import com.aptzip.review.dto.response.UserReviewListResponse;
import com.aptzip.review.entity.Review;
import com.aptzip.review.repository.DongCodeRepository;
import com.aptzip.review.repository.ReviewRepository;
import com.aptzip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final DongCodeRepository dongCodeRepository;

    public List<UserReviewListResponse> getUserReviewList(String userUuid) {
        return reviewRepository.findReviewsByUserUuid(userUuid);
    }

    public List<DongReviewListResponse> getDongReviewList(String dongCode) {
        return reviewRepository.findReviewsByDongCode(dongCode);
    }

    public void registReview(User user, RegistReviewRequest registReviewRequest) {
        DongCode dongCode = dongCodeRepository.findById(registReviewRequest.dongCode())
                .orElseThrow(() -> new IllegalArgumentException("Unexpected dongCode"));

        Review review = Review.builder()
                .user(user)
                .content(registReviewRequest.content())
                .dongCode(dongCode)
                .build();

        reviewRepository.save(review);
    }

    public void deleteReview(User user, String reviewUuid) {
        Review review = reviewRepository.findById(reviewUuid)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected review"));

        if (!review.getUser().getUserUuid().equals(user.getUserUuid())) {
            throw new AccessDeniedException("자신의 리뷰만 삭제할 수 있습니다.");
        }

        reviewRepository.delete(review);
    }
}
