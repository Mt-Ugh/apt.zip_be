package com.aptzip.dealMap.service;

import com.aptzip.dealMap.dto.query.DetailResult;
import com.aptzip.dealMap.dto.request.DealListRequest;
import com.aptzip.dealMap.dto.request.DetailRequest;
import com.aptzip.dealMap.dto.request.DongRequest;
import com.aptzip.dealMap.dto.request.GugunRequest;
import com.aptzip.dealMap.dto.response.*;
import com.aptzip.dealMap.repository.DealHouseRepository;
import com.aptzip.dealMap.repository.DealMapDongRepository;
import com.aptzip.dealMap.repository.DetailDealRepository;
import com.aptzip.dealMap.repository.DetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public List<SidoResponse> getDistinctSidoNames() {
        List<String> dongCodes = dealMapDongRepository.findSidoName();
        return dongCodes.stream().map(sidoName -> new SidoResponse(sidoName))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<GugunResponse> getGugunNamesBySidoName(GugunRequest gugunRequest) {
        List<String> dongCodes = dealMapDongRepository.findGugunNameBySidoName(gugunRequest.sidoName());
        return dongCodes.stream().map(gugunname -> new GugunResponse(gugunname))
                .distinct()
                .collect(Collectors.toList());
    }

    public List<DongResponse> getDongCodesBySidoAndGugun(DongRequest dongRequest) {
        String sidoName = dongRequest.sidoName();
        String gugunName = dongRequest.gugunName();

        List<Object[]> results = dealMapDongRepository.findBySidoNameAndGugunName(sidoName, gugunName);

        return results.stream()
                .map(result -> new DongResponse((String) result[0], (String) result[1]))
                .collect(Collectors.toList());
    }

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
                        (Integer) result[6],  // dealAmount
                        (Integer) result[7], // dealYear
                        (Integer) result[8], // dealMonth
                        (Integer) result[9], // dealDay
                        (Double) result[10], // latitude
                        (Double) result[11]  // longitude
                ))
                .collect(Collectors.toList());
    }

    public DetailResponse getDealDetailByAptSeq(DetailRequest detailRequest) {
        DetailResult house = detailRepository.findDetailByAptSeq(detailRequest.aptSeq());
        List<DetailDealListResponse> detailDealListResponse = detailDealRepository.findByHouseInfoAptSeq(detailRequest.aptSeq());

        return new DetailResponse(
                house.aptSeq(),
                house.aptNm(),
                house.sidoName(),
                house.gugunName(),
                house.dongName(),
                house.jibun(),
                house.maxAmount(),
                house.minAmount(),
                house.totalDeal(),
                house.buildYear(),
                detailDealListResponse
        );
    }
}
