package com.aptzip.dealMap.repository;

import com.aptzip.dealMap.dto.response.DetailDealListResponse;
import com.aptzip.dealMap.entity.HouseDeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailDealRepository extends JpaRepository<HouseDeal, Integer> {

    // 상세 조회의 실거래 내역 조회
    List<DetailDealListResponse> findByHouseInfoAptSeq(String aptSeq);
}
