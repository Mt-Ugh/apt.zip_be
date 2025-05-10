package com.aptzip.dealMap.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "house_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class HouseInfo {

    @Id
    @Column(name = "apt_seq", length = 20, nullable = false)
    private String aptSeq;

    @Column(name = "umd_nm", length = 20)
    private String umdNm;

    @Column(name = "jibun", length = 10)
    private String jibun;

    @Column(name = "road_nm_sgg_cd", length = 5)
    private String roadNmSggCd;

    @Column(name = "road_nm", length = 20)
    private String roadNm;

    @Column(name = "road_nm_bonbun", length = 10)
    private String roadNmBonbun;

    @Column(name = "road_nm_bubun", length = 10)
    private String roadNmBubun;

    @Column(name = "apt_nm", length = 40)
    private String aptNm;

    @Column(name = "build_year")
    private Integer buildYear;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "dong_code", referencedColumnName = "dong_code", nullable = false)
    private DongCode dongCode;
}
