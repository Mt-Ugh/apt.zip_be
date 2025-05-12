package com.aptzip.dealMap.service;

import com.aptzip.dealMap.dto.request.DealListRequest;
import com.aptzip.dealMap.dto.request.DetailRequest;
import com.aptzip.dealMap.dto.request.DongRequest;
import com.aptzip.dealMap.dto.request.GugunRequest;
import com.aptzip.dealMap.dto.response.*;
import com.aptzip.dealMap.entity.HouseDeal;
import com.aptzip.dealMap.repository.DealHouseRepository;
import com.aptzip.dealMap.repository.DealMapDongRepository;
import com.aptzip.dealMap.repository.DetailDealRepository;
import com.aptzip.dealMap.repository.DetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DealMapService {

    private final DealMapDongRepository dealMapDongRepository;
    private final DealHouseRepository dealHouseRepository;
    private final DetailRepository detailRepository;
    private final DetailDealRepository detailDealRepository;


    // 시도 이름 목록 조회
    public List<SidoResponse> getDistinctSidoNames() {
        List<String> dongCodes = dealMapDongRepository.findSidoName();
        return dongCodes.stream().map(sidoName -> new SidoResponse(sidoName))
                .distinct()
                .collect(Collectors.toList());
    }

    // 구군 이름으로 조회
    public List<GugunResponse> getGugunNamesBySidoName(GugunRequest gugunRequest) {
        List<String> dongCodes = dealMapDongRepository.findGugunNameBySidoName(gugunRequest.sidoName());
        return dongCodes.stream().map(gugunname -> new GugunResponse(gugunname))
                .distinct()
                .collect(Collectors.toList());
    }

    // 시도 이름과 구군 이름으로 조회
    public List<DongResponse> getDongCodesBySidoAndGugun(DongRequest dongRequest) {
        // DongRequest에서 sidoName과 gugunName 추출
        String sidoName = dongRequest.sidoName();
        String gugunName = dongRequest.gugunName();

        List<Object[]> results = dealMapDongRepository.findBySidoNameAndGugunName(sidoName, gugunName);

        return results.stream()
                .map(result -> new DongResponse((String) result[0], (String) result[1]))
                .collect(Collectors.toList());
    }

    //아파트 조회
    public List<DealListResponse> getDealsByAptNm(DealListRequest dealListRequest) {
        List<Object[]> results;
        if (dealListRequest.aptNm()==null){
            results = dealHouseRepository.findDealsByDongCode(dealListRequest.dongCode());
        }else{
            results = dealHouseRepository.findDealsByDongCodeAptNm(dealListRequest.dongCode(), dealListRequest.aptNm());
        }

        return results.stream()
                .map(result -> new DealListResponse(
                        (String) result[0],  // aptSeq
                        (String) result[1],  // aptNm
                        (String) result[2],  // sidoName
                        (String) result[3],  // gugunName
                        (String) result[4],  // dongName
                        (String) result[5],  // jibun
                        (String) result[6],  // dealAmount
                        (Integer) result[7], // dealYear
                        (Integer) result[8], // dealMonth
                        (Integer) result[9], // dealDay
                        (Double) result[10], // latitude
                        (Double) result[11]  // longitude
                ))
                .collect(Collectors.toList());
    }

    // 주택 상세 조회
    public DetailResponse getDetailByAptSeq(DetailRequest detailRequest) {
        List<Object[]> result = detailRepository.findDetailByAptSeq(detailRequest.aptSeq());

        Object[] row = result.get(0);

        return new DetailResponse(
                    (String) row[0],  // aptSeq
                    (String) row[1],  // aptNm
                    (String) row[2],  // sidoName
                    (String) row[3],  // gugunName
                    (String) row[4],  // dongName
                    (String) row[5],  // jibun
                    String.valueOf(row[6]),  // maxAmount
                    String.valueOf(row[7]),  // minAmount
                    ((Number) row[8]).longValue(),  // totalDeal
                    (Integer) row[9]  // buildYear
        );
    }

    // 주택 상세 실거래 내역 조회
    public List<DetailListResponse> getDealDetailByAptSeq(DetailRequest detailRequest) {

        List<Object[]> results = detailDealRepository.findByHouseInfoAptSeq(detailRequest.aptSeq());

        return results.stream()
                .map(result -> {
                    HouseDeal houseDeal = (HouseDeal) result[0];

                    return new DetailListResponse(
                            houseDeal.getNo(),               // no
                            houseDeal.getAptDong(),          // aptDong
                            houseDeal.getFloor(),            // floor
                            houseDeal.getDealYear(),         // dealYear
                            houseDeal.getDealMonth(),        // dealMonth
                            houseDeal.getDealDay(),          // dealDay
                            houseDeal.getExcluUseAr().floatValue(), // excluUseAr
                            houseDeal.getDealAmount()        // dealAmount
                    );
                })
                .collect(Collectors.toList());
    }
}
