package com.aptzip.qna.dto.response;

import com.aptzip.qna.constant.Category;

import java.time.LocalDateTime;

public record QnAListResponse(
        String qnaUuid,
        Category category,
        String title,
        LocalDateTime createdAt ) {
}
