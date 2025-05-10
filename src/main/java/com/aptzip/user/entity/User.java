package com.aptzip.user.entity;

import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE user_uuid = ?")
@Where(clause = "deleted_at IS NULL")
public class User implements UserDetails { // UserDetails를 상속받아 인증 객체로 사용

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "user_uuid", length = 36)
    private String userUuid;

    @Column(name = "name", length = 15, nullable = false)
    private String name;

    @Column(name = "nickname", length = 20)
    private String nickname;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "phone_number", length = 15, nullable = false)
    private String phoneNumber;

    @Column(name = "profile_url", length = 200)
    private String profileUrl;

    @CreatedDate
    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public User update(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.password = password;

        return this;
    }

    public void encodePassword(BCryptPasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
    }
}
