package com.aptzip.dealMap.dto.response;

import java.util.List;

public record DetailResponse(
        String aptSeq,
        String aptNm,
        String sidoName,
        String gugunName,
        String dongName,
        String jibun,
        Integer maxAmount,
        Integer minAmount,
        Long totalDeal,
        Integer buildYear,
        List<DetailDealListResponse> dealList){
}
