package com.aptzip.interestArea.controller;

import com.aptzip.interestArea.dto.request.AddInterestAreaRequest;
import com.aptzip.interestArea.dto.response.FameListResponse;
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

    @PostMapping("/regist")
    public ResponseEntity<Void> createInterestArea(@RequestBody AddInterestAreaRequest addInterestAreaRequest) {
        interestAreaService.save(addInterestAreaRequest);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = {"/fame/{userUuid}","/fame"})
    public ResponseEntity<List<FameListResponse>> getInterestArea(@PathVariable(required = false) String userUuid){
        List<FameListResponse> result = interestAreaService.getFame(userUuid);
        return ResponseEntity.ok(result);
    }

//    @GetMapping("/fame")
//    public ResponseEntity<List<FameListResponse>> getInterestArea(@AuthenticationPrincipal User user){
//        System.out.println("userUuid: " + user.getUserUuid());
//        List<FameListResponse> result = interestAreaService.getFame(user.getUserUuid());
//        return ResponseEntity.ok(result);
//    }
}
