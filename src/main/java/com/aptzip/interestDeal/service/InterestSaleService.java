package com.aptzip.interestDeal.service;

import com.aptzip.dealMap.entity.HouseDeal;
import com.aptzip.dealMap.repository.DealHouseRepository;
import com.aptzip.interestDeal.dto.request.DeleteInterestSaleRequest;
import com.aptzip.interestDeal.dto.request.RegistInterestSaleRequest;
import com.aptzip.interestDeal.dto.response.SaleListResponse;
import com.aptzip.interestDeal.entity.InterestSale;
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
    private final DealHouseRepository dealHouseRepository;

    public List<SaleListResponse> getUserSales(User user) {
        List<Object[]> results = interestSaleRepositiory.findByUserUuid(user.getUserUuid());

        return results.stream()
                .map(result -> new SaleListResponse(
                        (String) result[0],   //saleUuid
                        (Integer) result[1], //no
                        (String) result[2],  // aptNm
                        (String) result[3],  // sidoName
                        (String) result[4],  // gugunName
                        (String) result[5],  // dongName
                        (String) result[6],  // jibun
                        (Integer) result[7], // dealAmount
                        (Double) result[8], //latitude
                        (Double) result[9]   //longitude
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void saleSave(User user, RegistInterestSaleRequest registRequest){
        HouseDeal deal = dealHouseRepository.findById(registRequest.no())
                .orElseThrow(() -> new IllegalArgumentException("Unexpected no"));

        if(interestSaleRepositiory.findByUserAndHouseDeal(user, deal).isPresent()){
            throw new IllegalStateException("이미 관심 매물로 등록된 건입니다.");
        }

        InterestSale interestSale = InterestSale.builder()
                .user(user)
                .houseDeal(deal)
                .aptNm(registRequest.aptNm())
                .sidoName(registRequest.sidoName())
                .gugunName(registRequest.gugunName())
                .dongName(registRequest.dongName())
                .jibun(registRequest.jibun())
                .latitude(registRequest.latitude())
                .longitude(registRequest.longitude())
                .build();
        interestSaleRepositiory.save(interestSale);
    }

    @Transactional
    public void deleteSale(DeleteInterestSaleRequest deleteRequest){
        interestSaleRepositiory.deleteAllByIdInBatch(deleteRequest.saleUuid());
    }
}
