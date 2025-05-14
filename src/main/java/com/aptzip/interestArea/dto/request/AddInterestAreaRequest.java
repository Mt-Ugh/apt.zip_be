package com.aptzip.interestArea.dto.request;

import com.aptzip.interestArea.entity.Area;
import com.aptzip.interestArea.entity.InterestArea;
import com.aptzip.interestArea.entity.InterestAreaId;
import com.aptzip.user.entity.User;

import java.util.Date;

public record AddInterestAreaRequest(
        String userUuid,
        String areaUuid
) {

    // AddInterestAreaRequest -> InterestArea 엔티티로 변환
    public InterestArea toEntity(User user, Area area) {
        InterestAreaId id = new InterestAreaId(area.getAreaUuid(), user.getUserUuid());
        return InterestArea.builder()
                .interestAreaId(id)
                .areaUuid(area)
                .userUuid(user)
                .createdAt(new Date())
                .build();
    }
}
