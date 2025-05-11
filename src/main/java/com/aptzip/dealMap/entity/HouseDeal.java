package com.aptzip.dealMap.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "house_deal")
@Data
public class HouseDeal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "no",  nullable = false)
    private Integer no;

    @Column(name = "apt_dong", length = 40)
    private String aptDong;

    @Column(name = "floor", length = 3)
    private String floor;

    @Column(name = "deal_year")
    private Integer dealYear;

    @Column(name = "deal_month")
    private Integer dealMonth;

    @Column(name = "deal_day")
    private Integer dealDay;

    @Column(name = "exclu_use_ar")
    private Float excluUseAr;

    @Column(name = "deal_amount", length = 10)
    private String dealAmount;

    @ManyToOne
    @JoinColumn(name = "apt_seq", referencedColumnName = "apt_seq", insertable = false, updatable = false)
    private HouseInfo houseInfo;
}
