package com.aptzip.interestDeal.dto.request;

public record RegistInterestSaleRequest(
        Integer no,
        String dongCode,
        String aptNm,
        String sidoName,
        String gugunName,
        String dongName,
        String jibun,
        Double latitude,
        Double longitude) {
}
