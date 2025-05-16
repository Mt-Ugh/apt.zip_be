package com.aptzip.qna.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
public record QnAAnswerResponse(
        String qnaAnsUuid,
        String content,
        String profileUrl,
        String nickname,
        LocalDateTime createdAt) {
}
