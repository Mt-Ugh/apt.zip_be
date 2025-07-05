package com.aptzip.interestSale.controller;


import com.aptzip.interestSale.dto.request.DeleteInterestSaleRequest;
import com.aptzip.interestSale.dto.request.RegistInterestSaleRequest;
import com.aptzip.interestSale.dto.response.SaleListResponse;
import com.aptzip.interestSale.service.InterestSaleService;
import com.aptzip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/interestSale")
@RequiredArgsConstructor
public class InterestSaleController {

    private final InterestSaleService interestSaleService;

    @GetMapping("/list")
    public ResponseEntity<List<SaleListResponse>> getInterestSales(@AuthenticationPrincipal User user) {
        List<SaleListResponse> result = interestSaleService.getUserSales(user);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/")
    public ResponseEntity<Void> saveSale(@AuthenticationPrincipal User user, @RequestBody RegistInterestSaleRequest registRequest){
        interestSaleService.saleSave(user, registRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/")
    public ResponseEntity<Void> deleteSale(@RequestBody DeleteInterestSaleRequest deleteInterestSaleRequest){
        interestSaleService.deleteSale(deleteInterestSaleRequest);
        return ResponseEntity.ok().build();
    }
}
