package com.aptzip.interestArea.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record InterestListResponse(
        String areaName,
        String explain,
        String area_url,
        Double latitude,
        Double longitude,
        LocalDateTime createdAt) {
}
