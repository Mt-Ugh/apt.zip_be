package com.aptzip.dealMap.repository;

import com.aptzip.dealMap.entity.HouseDeal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailDealRepository extends JpaRepository<HouseDeal, Integer> {

    // 상세조회의 실거래 내역 조회
    List<Object[]> findByHouseInfoAptSeq(String aptSeq);
}
