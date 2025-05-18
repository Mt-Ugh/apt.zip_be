package com.aptzip.qna.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record QnAAnswerResponse(
        String qnaAnsUuid,
        String qnaContent,
        String profileUrl,
        String nickname,
        LocalDateTime createdAt,
        Integer isMineAns) {
}
