package com.aptzip.amenitiesMap.controller;

import com.aptzip.amenitiesMap.dto.request.AmenitiesListRequest;
import com.aptzip.amenitiesMap.dto.response.AmenitiesListResponse;
import com.aptzip.amenitiesMap.service.AmenitiesMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/amenitiesMap")
@RequiredArgsConstructor
public class AmenitiesMapController {

    private final AmenitiesMapService amenitiesMapService;

    @GetMapping("/list")
    public ResponseEntity<List<AmenitiesListResponse>> getList(@RequestParam String dongCode, @RequestParam List<String> majorCategory) {
        AmenitiesListRequest amenitiesListRequest = new AmenitiesListRequest(dongCode, majorCategory);
        List<AmenitiesListResponse> results = amenitiesMapService.getAmenitiesMapList(amenitiesListRequest);
        return ResponseEntity.ok(results);
    }
}
