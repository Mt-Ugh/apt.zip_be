package com.aptzip.interestDeal.controller;

import com.aptzip.interestDeal.dto.response.SaleListResponse;
import com.aptzip.interestDeal.entity.InterestSale;
import com.aptzip.interestDeal.service.InterestSaleService;
import com.aptzip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
