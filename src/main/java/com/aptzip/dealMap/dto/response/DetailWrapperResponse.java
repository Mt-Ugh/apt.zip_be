package com.aptzip.dealMap.dto.response;

import java.util.List;

public record DetailWrapperResponse(
        DetailResponse detailResponse,
        List<DetailListResponse> detailListResponse
) {
}
