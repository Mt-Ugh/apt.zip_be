package com.aptzip.interestDeal.entity;

import com.aptzip.dealMap.entity.HouseDeal;
import com.aptzip.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interest_sale")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestSale {

    @Id
    @Column(name = "sale_uuid", length = 36)
    private String saleUuid;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "no", referencedColumnName = "no", insertable = false, updatable = false)
    private HouseDeal houseDeal;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "user_uuid", nullable = false)
    private User user;
}
