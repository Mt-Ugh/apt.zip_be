package com.aptzip.interestDeal.dto.response;

public record SaleListResponse(
        String aptNm,
        String sidoName,
        String gugunName,
        String dongName,
        String jibun,
        Integer dealAmount) {
}
