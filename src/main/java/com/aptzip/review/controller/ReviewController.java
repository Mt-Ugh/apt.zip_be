package com.aptzip.review.controller;

import com.aptzip.review.dto.request.DongReviewListRequest;
import com.aptzip.review.dto.response.DongReviewListResponse;
import com.aptzip.review.dto.response.UserReviewListResponse;
import com.aptzip.review.service.ReviewService;
import com.aptzip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/list/user")
    public ResponseEntity<List<UserReviewListResponse>> getUserReviewList(@AuthenticationPrincipal User user) {
        List<UserReviewListResponse> userReviewResponse = reviewService.getUserReviewList(user.getUserUuid());
        return ResponseEntity.ok(userReviewResponse);
    }

    @GetMapping("/list/{dongCode}")
    public ResponseEntity<List<DongReviewListResponse>> getDongReviewList(@PathVariable("dongCode") DongReviewListRequest dongReviewListRequest) {
        List<DongReviewListResponse> dongReviewListResponse = reviewService.getDongReviewList(dongReviewListRequest);
        return ResponseEntity.ok(dongReviewListResponse);
    }
}
