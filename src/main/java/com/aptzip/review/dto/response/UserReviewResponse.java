package com.aptzip.review.dto.response;

import java.time.LocalDateTime;

public record UserReviewResponse(
        String reviewUuid,
        String dongName,
        String content,
        LocalDateTime createdAt) {
}
