package com.aptzip.interestArea.service;

import com.aptzip.interestArea.dto.request.AddInterestAreaRequest;
import com.aptzip.interestArea.entity.Area;
import com.aptzip.interestArea.entity.InterestArea;
import com.aptzip.interestArea.repositiory.AreaRepository;
import com.aptzip.interestArea.repositiory.InterestRepository;
import com.aptzip.user.entity.User;
import com.aptzip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InterestAreaService {

    private final InterestRepository interestRepository;
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;


    // 관심 지역 저장 메서드
    public String save(AddInterestAreaRequest dto) {
        // User와 Area 존재 여부 확인
        Optional<User> userOptional = userRepository.findById(dto.userUuid());
        Optional<Area> areaOptional = areaRepository.findById(dto.areaUuid());

        if (userOptional.isPresent() && areaOptional.isPresent()) {
            User user = userOptional.get();
            Area area = areaOptional.get();

            // DTO에서 Entity로 변환
            InterestArea interestArea = dto.toEntity(user, area);

            // InterestArea 저장
            interestRepository.save(interestArea);

            // 관심 지역의 areaUuid 반환
            return interestArea.getInterestAreaId().getAreaUuid();
        } else {
            throw new RuntimeException("User or Area not found");
        }
    }
}