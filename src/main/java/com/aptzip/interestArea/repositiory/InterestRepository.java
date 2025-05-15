package com.aptzip.interestArea.repositiory;

import com.aptzip.interestArea.entity.Area;
import com.aptzip.interestArea.entity.InterestArea;
import com.aptzip.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterestRepository extends JpaRepository<InterestArea, String> {

    // 인기 지역 (회원)
    @Query("SELECT " +
            "  a.areaUuid, " +
            "  a.name, " +
            "  a.areaUrl, " +
            "  CASE WHEN ia_user.userUuid.userUuid IS NOT NULL THEN 1 ELSE 0 END, " +
            "  COUNT(ia.areaUuid.areaUuid) " +
            "FROM Area a " +
            "LEFT JOIN InterestArea ia ON ia.areaUuid = a " +
            "LEFT JOIN InterestArea ia_user ON ia_user.areaUuid = a AND ia_user.userUuid.userUuid = :userUuid " +
            "GROUP BY a.areaUuid, a.name, a.areaUrl, ia_user.userUuid " +
            "ORDER BY COUNT(ia.areaUuid) DESC")
    List<Object[]> findMostPopularAreasUser(String userUuid);

    //인기 지역 (비회원)
    @Query("SELECT " +
            "  a.areaUuid, " +
            "  a.name, " +
            "  a.areaUrl " +
            "FROM Area a " +
            "LEFT JOIN InterestArea ia ON ia.areaUuid.areaUuid = a.areaUrl " +
            "GROUP BY a.areaUuid, a.name, a.areaUrl " +
            "ORDER BY COUNT(ia) DESC")
    List<Object[]> findMostPopularAreas();




    boolean existsByUserUuidAndAreaUuid(User userUuid, Area areaUuid);
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