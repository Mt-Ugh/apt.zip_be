package com.aptzip.interestArea.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Area {

    @Id
    @Column(name = "area_uuid", length = 36)
    private String areaUuid;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Column(name = "explain", columnDefinition = "TEXT")
    private String explain;

    @Column(name = "area_url", length = 200)
    private String areaUrl;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
}
