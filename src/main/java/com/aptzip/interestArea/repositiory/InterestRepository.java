package com.aptzip.interestArea.repositiory;

import com.aptzip.interestArea.entity.Area;
import com.aptzip.interestArea.entity.InterestArea;
import com.aptzip.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InterestRepository extends JpaRepository<InterestArea, String> {

    // 인기 지역 (회원)
    @Query("SELECT " +
            "  a.areaUuid, " +
            "  a.name, " +
            "  a.areaUrl, " +
            "  CASE WHEN ia_user.user.userUuid IS NOT NULL THEN 1 ELSE 0 END, " +
            "  COUNT(ia.area.areaUuid) " +
            "FROM Area a " +
            "LEFT JOIN InterestArea ia ON ia.area = a " +
            "LEFT JOIN InterestArea ia_user ON ia_user.area = a AND ia_user.user.userUuid = :userUuid " +
            "GROUP BY a.areaUuid, a.name, a.areaUrl, ia_user.user " +
            "ORDER BY COUNT(ia.area) DESC")
    List<Object[]> findMostPopularAreasUser(String userUuid);

    // 인기 지역 (비회원)
    @Query("SELECT " +
            "  a.areaUuid, " +
            "  a.name, " +
            "  a.areaUrl " +
            "FROM Area a " +
            "LEFT JOIN InterestArea ia ON ia.area.areaUuid = a.areaUrl " +
            "GROUP BY a.areaUuid, a.name, a.areaUrl " +
            "ORDER BY COUNT(ia) DESC")
    List<Object[]> findMostPopularAreas();
    
    boolean existsByUserAndArea(User user, Area area);

    @Modifying
    @Query("delete from InterestArea ia where ia.user.userUuid = :userUuid and ia.area.areaUuid = :areaUuid")
    void deleteByUserUuidAndAreaUuid(String userUuid, String areaUuid);

    List<InterestArea> findByUser(User user);
}