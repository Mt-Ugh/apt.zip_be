package com.aptzip.amenitiesMap.service;


import com.aptzip.amenitiesMap.dto.request.AmenitiesListRequest;
import com.aptzip.amenitiesMap.dto.response.AmenitiesListResponse;
import com.aptzip.amenitiesMap.repository.AmenitiesMapRepository;
import com.aptzip.dealMap.entity.DongCode;
import com.aptzip.dealMap.repository.DealMapDongRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AmenitiesMapService {

    private final AmenitiesMapRepository amenitiesMapRepository;
    private final  DealMapDongRepository dealMapDongRepository;

    public List<AmenitiesListResponse> getAmenitiesMapList(AmenitiesListRequest amenitiesListRequest) {
        DongCode dongCode = dealMapDongRepository.findById(amenitiesListRequest.dongCode())
                .orElseThrow(() -> new IllegalArgumentException("Unexpected dongCode"));
        String majorCategory = amenitiesListRequest.majorCategory();

       return amenitiesMapRepository.findByDongCodeAndMajorCategory(dongCode,majorCategory);
    }
}
