package com.aptzip.dealMap.dto.response;

public record DetailDealListResponse(
        Integer no,
        String aptDong,
        String floor,
        Integer dealYear,
        Integer dealMonth,
        Integer dealDay,
        Float excluUseAr,
        Integer dealAmount){
}
