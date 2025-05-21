package com.aptzip.dealMap.repository;

import com.aptzip.dealMap.dto.query.DetailResult;
import com.aptzip.dealMap.entity.HouseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DetailRepository extends JpaRepository<HouseInfo, Integer> {

    // 주택 상세 정보 조회
    @Query("""
    SELECT new com.aptzip.dealMap.dto.query.DetailResult(
        hi.aptSeq,
        hi.aptNm,
        dc.sidoName,
        dc.gugunName,
        dc.dongName,
        hi.jibun,
        MAX(hd.dealAmount),
        MIN(hd.dealAmount),
        COUNT(hd),
        hi.buildYear
    )
    FROM HouseInfo hi
    JOIN HouseDeal hd ON hd.houseInfo.aptSeq = hi.aptSeq
    JOIN DongCode dc ON dc.dongCode = hi.dongCode.dongCode
    WHERE hi.aptSeq = :aptSeq
    GROUP BY hi.aptSeq
    """)
    DetailResult findDetailByAptSeq(@Param("aptSeq") String aptSeq);
}
