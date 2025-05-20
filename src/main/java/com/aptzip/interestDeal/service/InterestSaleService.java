package com.aptzip.interestDeal.service;

import com.aptzip.interestDeal.dto.response.SaleListResponse;
import com.aptzip.interestDeal.respository.InterestSaleRepositiory;
import com.aptzip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestSaleService {

    private final InterestSaleRepositiory interestSaleRepositiory;

    public List<SaleListResponse> getUserSales(User user) {
        List<Object[]> results = interestSaleRepositiory.findByUserUuid(user.getUserUuid());

        return results.stream()
                .map(result -> new SaleListResponse(
                        (String) result[0],  // aptNm
                        (String) result[1],  // sidoName
                        (String) result[2],  // gugunName
                        (String) result[3],  // dongName
                        (String) result[4],  // jibun
                        (Integer) result[5] // dealAmount
                ))
                .collect(Collectors.toList());
    }

}
