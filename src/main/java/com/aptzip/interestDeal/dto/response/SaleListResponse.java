package com.aptzip.interestDeal.dto.response;

public record SaleListResponse(
        String saleUuid,
        Integer no,
        String aptNm,
        String sidoName,
        String gugunName,
        String dongName,
        String jibun,
        Integer dealAmount,
        Double latitude,
        Double longitude) {
}
