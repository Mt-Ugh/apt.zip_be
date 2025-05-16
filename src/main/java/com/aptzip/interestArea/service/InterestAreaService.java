package com.aptzip.interestArea.service;

import com.aptzip.interestArea.dto.request.AddInterestAreaRequest;
import com.aptzip.interestArea.dto.response.FameListResponse;
import com.aptzip.interestArea.dto.response.InterestListResponse;
import com.aptzip.interestArea.entity.Area;
import com.aptzip.interestArea.entity.InterestArea;
import com.aptzip.interestArea.repositiory.AreaRepository;
import com.aptzip.interestArea.repositiory.InterestRepository;
import com.aptzip.user.entity.User;
import com.aptzip.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterestAreaService {

    private final InterestRepository interestRepository;
    private final UserRepository userRepository;
    private final AreaRepository areaRepository;


    public List<InterestListResponse> getInterestAreaByUser(User user) {
        List<InterestArea> interestAreas = interestRepository.findByUser(user);

        return interestAreas.stream()
                .map(ia -> InterestListResponse.builder()
                        .areaName(ia.getArea().getName())
                        .explain(ia.getArea().getExplain())
                        .area_url(ia.getArea().getAreaUrl())
                        .latitude(ia.getArea().getLatitude())
                        .longitude(ia.getArea().getLongitude())
                        .created_at(ia.getCreatedAt())
                        .build()
                )
                .toList();
    }


    @Transactional
    public String save(User user, AddInterestAreaRequest addInterestAreaRequest) {
        Area area = areaRepository.findById(addInterestAreaRequest.areaUuid())
                .orElseThrow(() -> new IllegalArgumentException("Unexpected area"));

        InterestArea interestArea = addInterestAreaRequest.toEntity(user,area);

        boolean exists = interestRepository.existsByUserAndArea(interestArea.getUser(),interestArea.getArea());
        if (exists) {
            throw new IllegalStateException("InterestArea already exists for this user and area");
        }

        return interestRepository.save(interestArea).getIaUuid();
    }

    public List<FameListResponse> getFame(String userUuid){
        List<Object[]> results;
        if(userUuid!=null){
            results = interestRepository.findMostPopularAreasUser(userUuid);
            return results.stream()
                    .map(result -> new FameListResponse(
                            (String) result[0], //areaUuid
                            (String) result[1], //name
                            (String) result[2], //areaUrl
                            (Integer) result[3] //isInterest
                    ))
                    .collect(Collectors.toList());
        }else{
            results = interestRepository.findMostPopularAreas();


            return results.stream()
                    .map(result -> new FameListResponse(
                            (String) result[0], //areaUuid
                            (String) result[1], //name
                            (String) result[2], //areaUrl
                            (Integer) 0
                    ))
                    .collect(Collectors.toList());
        }
    }

    @Transactional
    public void disableArea(String userUuid, String areaUuid){
        interestRepository.deleteByUserUuidAndAreaUuid(userUuid,areaUuid);
    }
}