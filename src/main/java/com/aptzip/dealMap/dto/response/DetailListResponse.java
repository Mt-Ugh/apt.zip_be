package com.aptzip.dealMap.dto.response;

import java.math.BigDecimal;

public record DetailListResponse(
        Integer no,
        String aptDong,
        String floor,
        Integer dealYear,
        Integer dealMonth,
        Integer dealDay,
        Float excluUseAr,
        String dealAmount){
}
