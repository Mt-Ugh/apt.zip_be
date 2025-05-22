package com.aptzip.interestDeal.dto.response;

public record SaleListResponse(
        String saleUuid,
        String dongCode,
        Integer no,
        String aptSeq,
        String aptNm,
        String sidoName,
        String gugunName,
        String dongName,
        String jibun,
        Integer dealYear,
        Integer dealMonth,
        Integer dealDay,
        Integer dealAmount,
        Double latitude,
        Double longitude) {
}
