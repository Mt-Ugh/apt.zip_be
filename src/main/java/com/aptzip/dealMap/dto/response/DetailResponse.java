package com.aptzip.dealMap.dto.response;

public record DetailResponse(
        String aptSeq,      // 아파트 시퀀스
        String aptNm,       // 아파트 이름
        String sidoName,    // 시도 이름
        String gugunName,   // 구군 이름
        String dongName,    // 동 이름
        String jibun,       // 지번
        String maxAmount,   // 최대 거래 금액
        String minAmount,   // 최소 거래 금액
        Long totalDeal,     // 거래 수
        Integer buildYear){ // 건축 연도

}
