package com.aptzip.amenitiesMap.entity;

import com.aptzip.dealMap.entity.DongCode;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="commercial_place")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class CommercialPlace {

    @Id
    @Column(name = "cp_id", length = 30, nullable = false)
    private String cpId;

    @Column(name = "place_name", length = 255, nullable = false)
    private String placeName;

    @Column(name = "branch_name", length = 255)
    private String branchName;

    @Column(name = "major_category", length = 50)
    private String majorCategory;

    @Column(name = "mid_category", length = 50)
    private String midCategory;

    @Column(name = "small_category", length = 100)
    private String smallCategory;

    @Column(name = "road_address", length = 255)
    private String roadAddress;

    @Column(name = "jibun_address", length = 255)
    private String jibunAddress;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "dong_code", referencedColumnName = "dong_code", nullable = false)
    private DongCode dongCode;
}
