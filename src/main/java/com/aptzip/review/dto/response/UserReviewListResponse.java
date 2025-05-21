package com.aptzip.review.dto.response;

import java.time.LocalDateTime;

public record UserReviewListResponse(
        String reviewUuid,
        String dongName,
        String dongCode,
        String content,
        LocalDateTime createdAt) {
}
