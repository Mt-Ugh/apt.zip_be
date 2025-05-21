package com.aptzip.review.dto.response;

import java.time.LocalDateTime;

public record UserReviewListResponse(
        String reviewUuid,
        String dongName,
        String content,
        LocalDateTime createdAt) {
}
