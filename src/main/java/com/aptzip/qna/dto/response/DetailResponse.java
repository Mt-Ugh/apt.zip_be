package com.aptzip.qna.dto.response;

import com.aptzip.qna.constant.Category;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record DetailResponse(
        Category category,
        String title,
        String content,
        String profileUrl,
        String nickname,
        LocalDateTime createdAt,
        Integer isMineQnA,
        List<QnAAnswerResponse> qnaAnswerResponse) {
}
