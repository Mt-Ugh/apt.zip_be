package com.aptzip.qna.dto.response;

import java.util.List;

public record DetailWrapperResponse(
        DetailResponse detailResponse,
        List<QnAAnswerResponse>  qnAAnswerResponse) {
}
