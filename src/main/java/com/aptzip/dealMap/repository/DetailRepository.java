package com.aptzip.dealMap.repository;

import com.aptzip.dealMap.entity.HouseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DetailRepository extends JpaRepository<HouseInfo, Integer> {

    // 주택 상세정보 조회
    @Query("SELECT " +
            "hi.aptSeq, hi.aptNm, dc.sidoName, " +
            "dc.gugunName, dc.dongName, hi.jibun, "+
            "MAX(hd.dealAmount), MIN(hd.dealAmount), COUNT(hd), hi.buildYear " +
            "FROM HouseInfo hi " +
            "JOIN HouseDeal hd on hd.houseInfo.aptSeq = hi.aptSeq " +
            "JOIN DongCode dc on dc.dongCode = hi.dongCode.dongCode " +
            "WHERE hi.aptSeq = :aptSeq " +
            "GROUP BY hi.aptSeq, hi.aptNm, dc.sidoName, dc.gugunName, dc.dongName, hi.jibun, hi.buildYear")
    List<Object[]> findDetailByAptSeq(String aptSeq);


}
