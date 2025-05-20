package com.aptzip.dealMap.repository;

import com.aptzip.dealMap.entity.HouseDeal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealHouseRepository extends JpaRepository<HouseDeal, Integer> {

    // 법정동 코드로 거래 정보 조회
    @Query("SELECT " +
            "hi.aptSeq, hi.aptNm, dc.sidoName, dc.gugunName, dc.dongName, hi.jibun, " +
            "MAX(hd.dealAmount), MAX(hd.dealYear), MAX(hd.dealMonth), MAX(hd.dealDay), " +
            "hi.latitude, hi.longitude " +
            "FROM HouseDeal hd " +
            "JOIN HouseInfo hi ON hi.aptSeq = hd.houseInfo.aptSeq " +
            "JOIN DongCode dc ON hi.dongCode.dongCode = dc.dongCode " +
            "WHERE dc.dongCode = :dongCode " +
            "GROUP BY hi.aptSeq, hi.aptNm, dc.sidoName, dc.gugunName, dc.dongName, hi.jibun, hi.latitude, hi.longitude " +
            "ORDER BY MAX(hd.dealYear) DESC, MAX(hd.dealMonth) DESC, MAX(hd.dealDay) DESC")
    List<Object[]> findDealsByDongCode(String dongCode);

    // 법정동 코드와 아파트 이름으로 거래 정보 조회
    @Query("SELECT " +
            "hi.aptSeq, hi.aptNm, dc.sidoName, dc.gugunName, dc.dongName, hi.jibun, " +
            "MAX(hd.dealAmount), MAX(hd.dealYear), MAX(hd.dealMonth), MAX(hd.dealDay), " +
            "hi.latitude, hi.longitude " +
            "FROM HouseDeal hd " +
            "JOIN HouseInfo hi ON hi.aptSeq = hd.houseInfo.aptSeq " +
            "JOIN DongCode dc ON hi.dongCode.dongCode = dc.dongCode " +
            "WHERE hi.aptNm like %:aptNm% " +
            "AND dc.dongCode = :dongCode " +
            "GROUP BY hi.aptSeq, hi.aptNm, dc.sidoName, dc.gugunName, dc.dongName, hi.jibun, hi.latitude, hi.longitude " +
            "ORDER BY MAX(hd.dealYear) DESC, MAX(hd.dealMonth) DESC, MAX(hd.dealDay) DESC")
    List<Object[]> findDealsByDongCodeAptNm(String dongCode, String aptNm);
}
