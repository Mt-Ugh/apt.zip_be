package com.aptzip.interestArea.entity;

import com.aptzip.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "interest_area")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterestArea {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "ia_uuid", length = 36)
    private String iaUuid;

    @ManyToOne
    @JoinColumn(name = "area_uuid", referencedColumnName = "area_uuid", nullable = false)
    private Area area;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "user_uuid", nullable = false)
    private User user;

    @CreatedDate
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
