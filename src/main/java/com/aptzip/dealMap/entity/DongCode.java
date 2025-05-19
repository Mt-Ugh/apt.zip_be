package com.aptzip.dealMap.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="dongcode")
public class DongCode {

    @Id
    @Column(name="dong_code", length = 10)
    private String dongCode;

    @Column(name="sido_name", length = 30)
    private String sidoName;

    @Column(name = "gugun_name", length = 30)
    private String gugunName;

    @Column(name = "dong_name", length = 30)
    private String dongName;
}
