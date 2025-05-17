package com.aptzip.amenitiesMap.repository;

import com.aptzip.amenitiesMap.dto.response.AmenitiesListResponse;
import com.aptzip.amenitiesMap.entity.CommercialPlace;
import com.aptzip.dealMap.entity.DongCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmenitiesMapRepository extends JpaRepository<CommercialPlace, String> {
    List<AmenitiesListResponse> findByDongCodeAndMajorCategory(DongCode dongCode, String majorCategory);
}
