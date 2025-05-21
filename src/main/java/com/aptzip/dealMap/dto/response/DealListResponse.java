package com.aptzip.dealMap.dto.response;

public record DealListResponse(
        String aptSeq,
        String aptNm,
        String sidoName,
        String gugunName,
        String dongName,
        String jibun,
        Integer dealAmount,
        Integer dealYear,
        Integer dealMonth,
        Integer dealDay,
        Double latitude,
        Double longitude
) {}