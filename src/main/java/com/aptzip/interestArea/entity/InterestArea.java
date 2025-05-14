package com.aptzip.interestArea.entity;

import com.aptzip.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "interest_area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestArea {

    @EmbeddedId
    private InterestAreaId interestAreaId; // 복합 키

    @ManyToOne
    @MapsId("areaUuid") // 복합 키에서의 외래 키 매핑
    @JoinColumn(name = "area_uuid", referencedColumnName = "area_uuid", nullable = false)
    private Area areaUuid;

    @ManyToOne
    @MapsId("userUuid") // 복합 키에서의 외래 키 매핑
    @JoinColumn(name = "user_uuid", referencedColumnName = "user_uuid", nullable = false)
    private User userUuid;

    @Column(name = "created_at")
    private Date createdAt;
}
