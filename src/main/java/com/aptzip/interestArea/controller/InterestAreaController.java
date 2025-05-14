package com.aptzip.interestArea.controller;

import com.aptzip.interestArea.dto.request.AddInterestAreaRequest;
import com.aptzip.interestArea.service.InterestAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/interestArea")
@RequiredArgsConstructor
public class InterestAreaController {

    private final InterestAreaService interestAreaService;

    @PostMapping("/regist")
    public ResponseEntity<Void> createInterestArea(@RequestBody AddInterestAreaRequest dto) {
        // 관심 지역 저장
        interestAreaService.save(dto);

        // HTTP 201 Created 응답 반환
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
