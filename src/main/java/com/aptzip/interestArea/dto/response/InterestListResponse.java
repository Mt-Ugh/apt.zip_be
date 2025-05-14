package com.aptzip.interestArea.dto.response;

import java.util.Date;

public record InterestListResponse(
        String userUuid,
        String areaName,
        String explain,
        String area_url,
        Double latitude,
        Double longitude,
        Date created_at) {
}
