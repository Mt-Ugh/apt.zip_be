package com.aptzip.interestSale.entity;

import com.aptzip.dealMap.entity.HouseDeal;
import com.aptzip.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "interest_sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestSale {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "sale_uuid", length = 36)
    private String saleUuid;

    @Column(name="dong_code", length = 10)
    private String dongCode;

    @Column(name = "apt_nm", length = 40)
    private String aptNm;

    @Column(name = "sido_name", length = 30)
    private String sidoName;

    @Column(name = "gugun_name", length = 30)
    private String gugunName;

    @Column(name = "dong_name", length = 30)
    private String dongName;

    @Column(name = "jibun", length = 10)
    private String jibun;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @ManyToOne
    @JoinColumn(name = "no", referencedColumnName = "no")
    private HouseDeal houseDeal;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "user_uuid", nullable = false)
    private User user;
}
