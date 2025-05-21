package com.aptzip.review.dto.response;

import java.time.LocalDateTime;

public record DongReviewListResponse(
        String reviewUuid,
        String content,
        LocalDateTime createdAt,
        String profileUrl,
        String nickname) {
}
