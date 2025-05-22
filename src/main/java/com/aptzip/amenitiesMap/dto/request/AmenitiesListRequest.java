package com.aptzip.amenitiesMap.dto.request;

import java.util.List;

public record AmenitiesListRequest(
        String dongCode,
        List<String> majorCategory) {
}
