package com.aptzip.qna.entity;

import com.aptzip.qna.constant.Category;
import com.aptzip.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Table(name="qna")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QnA {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name="qna_uuid", length = 36)
    private String qnaUuid;

    @Column(name="title", length = 255)
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @CreatedDate
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "user_uuid", nullable = false)
    private User user;
}
