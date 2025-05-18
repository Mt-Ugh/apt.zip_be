package com.aptzip.interestArea.controller;

import com.aptzip.interestArea.dto.request.AddInterestAreaRequest;
import com.aptzip.interestArea.dto.response.FameListResponse;
import com.aptzip.interestArea.dto.response.InterestListResponse;
import com.aptzip.interestArea.service.InterestAreaService;
import com.aptzip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/interestArea")
@RequiredArgsConstructor
public class InterestAreaController {

    private final InterestAreaService interestAreaService;

    @GetMapping("/list")
    public ResponseEntity<List<InterestListResponse>> getUserInterestArea(@AuthenticationPrincipal User user){
        List<InterestListResponse> results = interestAreaService.getInterestAreaByUser(user);
        return ResponseEntity.ok(results);
    }

    @PostMapping("/regist")
    public ResponseEntity<Void> createInterestArea(@AuthenticationPrincipal User user, @RequestBody AddInterestAreaRequest addInterestAreaRequest) {
        interestAreaService.save(user, addInterestAreaRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/fame")
    public ResponseEntity<List<FameListResponse>> getInterestArea(@AuthenticationPrincipal User user){
        String userUuid = user != null ? user.getUserUuid() : null;
        List<FameListResponse> result = interestAreaService.getFame(userUuid);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{areaUuid}")
    public ResponseEntity<Void> disableArea(@AuthenticationPrincipal User user, @PathVariable("areaUuid") String areaUuid){
        interestAreaService.disableArea(user.getUserUuid(), areaUuid);
        return ResponseEntity.ok().build();
    }
}
