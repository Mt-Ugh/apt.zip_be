package com.aptzip.review.controller;

import com.aptzip.review.dto.request.RegistReviewRequest;
import com.aptzip.review.dto.response.DongReviewListResponse;
import com.aptzip.review.dto.response.UserReviewListResponse;
import com.aptzip.review.service.ReviewService;
import com.aptzip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/list/dong/{dongCode}")
    public ResponseEntity<List<DongReviewListResponse>> getDongReviewList(@PathVariable("dongCode") String dongCode) {
        List<DongReviewListResponse> dongReviewListResponse = reviewService.getDongReviewList(dongCode);
        return ResponseEntity.ok(dongReviewListResponse);
    }

    @PostMapping("/regist")
    public ResponseEntity<Void> registReview(@AuthenticationPrincipal User user, @RequestBody RegistReviewRequest registReviewRequest) {
        reviewService.registReview(user, registReviewRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{reviewUuid}")
    public ResponseEntity<Void> deleteReview(@AuthenticationPrincipal User user, @PathVariable("reviewUuid") String reviewUuid) {
        reviewService.deleteReview(user, reviewUuid);
        return ResponseEntity.ok().build();
    }
}
