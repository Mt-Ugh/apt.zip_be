package com.aptzip.interestArea.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InterestListResponse(
        String areaUuid,
        String areaName,
        String explain,
        String areaUrl,
        Double latitude,
        Double longitude,
        LocalDateTime createdAt) {
}
