package com.aptzip.interestDeal.dto.response;

public record SaleListResponse(
        String saleUuid,
        Integer no,
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
        Double longitude) {
}
