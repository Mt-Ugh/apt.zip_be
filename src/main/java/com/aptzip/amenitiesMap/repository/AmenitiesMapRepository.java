package com.aptzip.amenitiesMap.repository;

import com.aptzip.amenitiesMap.dto.response.AmenitiesListResponse;
import com.aptzip.amenitiesMap.entity.CommercialPlace;
import com.aptzip.dealMap.entity.DongCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmenitiesMapRepository extends JpaRepository<CommercialPlace, String> {

    @Query("""
            SELECT new com.aptzip.amenitiesMap.dto.response.AmenitiesListResponse(
                c.placeName,
                c.majorCategory,
                c.midCategory,
                c.smallCategory,
                c.jibunAddress,
                c.latitude,
                c.longitude
            )
            FROM CommercialPlace c
            WHERE c.dongCode = :dongCode
                AND c.majorCategory IN :majorCategory
            """)
    List<AmenitiesListResponse> findByDongCodeAndMajorCategory(DongCode dongCode, List<String> majorCategory);
}
