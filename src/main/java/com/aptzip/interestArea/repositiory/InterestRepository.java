package com.aptzip.interestArea.repositiory;

import com.aptzip.interestArea.entity.InterestArea;
import com.aptzip.interestArea.entity.InterestAreaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InterestRepository extends JpaRepository<InterestArea, InterestAreaId> {

//    // 인기 지역 (areaUuid가 가장 많이 등장하는 지역 순으로)
//    @Query("SELECT ia.area.areaUuid AS areaUuid, COUNT(ia) AS interestCount " +
//            "FROM InterestArea ia " +
//            "GROUP BY ia.area.areaUuid " +
//            "ORDER BY interestCount DESC")
//    List<Object[]> findMostPopularAreas(); // areaUuid와 관심 수를 가져오는 쿼리
//
//    // 사용자별 관심 지역 (userUuid와 areaUuid를 기반으로 해당 지역에 대한 관심이 있는지 확인)
//    @Query("SELECT ia, " +
//            "CASE WHEN ia.user.userUuid = :userUuid THEN 1 ELSE 0 END AS isInterested " +
//            "FROM InterestArea ia " +
//            "WHERE ia.user.userUuid = :userUuid AND ia.area.userUuid = :areaUuid")
//    List<Object[]> findInterestStatusByUserAndArea(String userUuid, String areaUuid);
//
//    // 사용자별 관심 지역 목록 (userUuid에 해당하는 모든 관심 지역 조회)
//    List<InterestArea> findByUserUuid(String userUuid);
//
//    // 특정 사용자와 특정 지역에 대한 관심 지역 조회
//    List<InterestArea> findByUserUuidAndAreaUuid(String userUuid, String areaUuid);

}