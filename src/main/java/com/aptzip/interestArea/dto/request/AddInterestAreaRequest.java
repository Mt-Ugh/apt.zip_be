package com.aptzip.interestArea.dto.request;

import com.aptzip.interestArea.entity.Area;
import com.aptzip.interestArea.entity.InterestArea;
import com.aptzip.user.entity.User;

import java.util.Date;

public record AddInterestAreaRequest(
        String userUuid,
        String areaUuid
    ) {

    public InterestArea toEntity(User user, Area area) {
        return InterestArea.builder()
                .areaUuid(area)
                .userUuid(user)
                .build();
    }
}
