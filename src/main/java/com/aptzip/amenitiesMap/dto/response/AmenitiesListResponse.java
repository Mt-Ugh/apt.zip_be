package com.aptzip.amenitiesMap.dto.response;

public record AmenitiesListResponse(
        String placeName,
        String majorCategory,
        String midCategory,
        String smallCategory,
        String jibunAddress,
        Double latitude,
        Double longitude) {
}
