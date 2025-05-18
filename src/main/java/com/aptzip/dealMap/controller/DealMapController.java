package com.aptzip.dealMap.controller;

import com.aptzip.dealMap.dto.request.DealListRequest;
import com.aptzip.dealMap.dto.request.DetailRequest;
import com.aptzip.dealMap.dto.request.DongRequest;
import com.aptzip.dealMap.dto.request.GugunRequest;
import com.aptzip.dealMap.dto.response.*;
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
    public ResponseEntity<List<SidoResponse>> getSidoList() {
        List<SidoResponse> sidoList = dealMapService.getDistinctSidoNames();
        return ResponseEntity.ok(sidoList);
    }

    @GetMapping("/gugunList")
    public ResponseEntity<List<GugunResponse>> getGugunList(@RequestParam("sidoName") GugunRequest sidoName) {
        List<GugunResponse> gugunList = dealMapService.getGugunNamesBySidoName(sidoName);
        return ResponseEntity.ok(gugunList);  // GugunResponse 리스트를 반환
    }

    @GetMapping("/dongList")
    public ResponseEntity<List<DongResponse>> getDongList(@RequestParam("sidoName") String sidoName, @RequestParam("gugunName") String gugunName) {
        DongRequest dongRequest = new DongRequest(sidoName, gugunName);
        List<DongResponse> dongList = dealMapService.getDongCodesBySidoAndGugun(dongRequest);
        return ResponseEntity.ok(dongList);
    }

    @GetMapping("/list")
    public ResponseEntity<List<DealListResponse>> getAptList(@RequestParam("dongCode") String dongCode, @RequestParam(value = "aptNm", required = false) String aptNm) {
        DealListRequest dealListRequest = new DealListRequest(dongCode, aptNm);
        List<DealListResponse> results = dealMapService.getDealsByAptNm(dealListRequest);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/detail")
    public ResponseEntity<DetailWrapperResponse> getDealDetail(@RequestParam("aptSeq") DetailRequest aptSeq) {
        DetailResponse detailResponse = dealMapService.getDetailByAptSeq(aptSeq);
        List<DetailListResponse> detailListResponse = dealMapService.getDealDetailByAptSeq(aptSeq);

        DetailWrapperResponse wrapperResponse = new DetailWrapperResponse(detailResponse, detailListResponse);
        return ResponseEntity.ok(wrapperResponse);
    }
}
