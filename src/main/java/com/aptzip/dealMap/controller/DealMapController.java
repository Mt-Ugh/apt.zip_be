package com.aptzip.dealMap.controller;

import com.aptzip.dealMap.dto.request.DongRequest;
import com.aptzip.dealMap.dto.request.GugunRequest;
import com.aptzip.dealMap.dto.response.DongResponse;
import com.aptzip.dealMap.dto.response.GugunResponse;
import com.aptzip.dealMap.dto.response.SidoResponse;
import com.aptzip.dealMap.service.DealMapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/dealMap")
@RequiredArgsConstructor
public class DealMapController {

    private final DealMapService dealMapService;

    @GetMapping("/sidoList")
    public ResponseEntity<List<SidoResponse>> getSidoList(){
        List<SidoResponse> sidoList = dealMapService.getDistinctSidoNames();
        return ResponseEntity.ok(sidoList);
    }

    @GetMapping("/gugunList")
    public ResponseEntity<List<GugunResponse>> getGugunList(@RequestParam GugunRequest sidoName){
        List<GugunResponse> gugunList = dealMapService.getGugunNamesBySidoName(sidoName);
        return ResponseEntity.ok(gugunList);  // GugunResponse 리스트를 반환
    }

    @GetMapping("/dongList")
    public ResponseEntity<List<DongResponse>> getDongList(@RequestParam("sidoName") String sidoName, @RequestParam("gugunName") String gugunName){
        DongRequest dongRequest = new DongRequest(sidoName, gugunName);
        List<DongResponse> dongList = dealMapService.getDongCodesBySidoAndGugun(dongRequest);
        return ResponseEntity.ok(dongList);
    }
}
