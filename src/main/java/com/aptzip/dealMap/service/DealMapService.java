package com.aptzip.dealMap.service;

import com.aptzip.dealMap.dto.response.DongResponse;
import com.aptzip.dealMap.dto.response.GugunResponse;
import com.aptzip.dealMap.dto.response.SidoResponse;
import com.aptzip.dealMap.repository.DealMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DealMapService {

    private final DealMapRepository dealMapRepository;

    // 시도 이름 목록 조회
    public List<SidoResponse> getDistinctSidoNames() {
        List<String> dongCodes = dealMapRepository.findSidoName();
        return dongCodes.stream().map(sidoName -> new SidoResponse(sidoName))
                .distinct()
                .collect(Collectors.toList());
    }

    // 구군 이름으로 조회
    public List<GugunResponse> getGugunNamesBySidoName(String sidoName) {
        List<String> dongCodes = dealMapRepository.findGugunNameBySidoName(sidoName);
        return dongCodes.stream().map(gugunname -> new GugunResponse(gugunname))
                .distinct()
                .collect(Collectors.toList());
    }

    // 시도 이름과 구군 이름으로 조회
    public List<DongResponse> getDongCodesBySidoAndGugun(String sidoName, String gugunName) {
        List<Object[]> dongCodes = dealMapRepository.findBySidoNameAndGugunName(sidoName,gugunName);
        return dongCodes.stream()
                .map(result -> new DongResponse((String) result[0], (String) result[1])) // 0: dong_code, 1: dong_name
                .distinct()
                .collect(Collectors.toList());
    }
}
