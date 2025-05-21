package com.aptzip.dealMap.dto.query;

public record DetailResult(
        String aptSeq,
        String aptNm,
        String sidoName,
        String gugunName,
        String dongName,
        String jibun,
        Integer maxAmount,
        Integer minAmount,
        Long totalDeal,
        Integer buildYear) {
}