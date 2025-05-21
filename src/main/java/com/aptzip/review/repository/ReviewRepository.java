package com.aptzip.review.repository;

import com.aptzip.review.dto.response.DongReviewListResponse;
import com.aptzip.review.dto.response.UserReviewListResponse;
import com.aptzip.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    @Query("""
            SELECT new com.aptzip.review.dto.response.UserReviewListResponse(
                r.reviewUuid,
                d.dongName,
                r.content,
                r.createdAt
            )
            FROM Review r
            JOIN r.dongCode d
            WHERE r.user.userUuid = :userUuid
            ORDER BY r.createdAt DESC
            """)
    List<UserReviewListResponse> findReviewsByUserUuid(@Param("userUuid") String userUuid);

    @Query("""
            SELECT new com.aptzip.review.dto.response.DongReviewListResponse(
                r.reviewUuid,
                r.content,
                r.createdAt,
                u.profileUrl,
                u.nickname
            )
            FROM Review r
            JOIN r.user u
            WHERE r.dongCode.dongCode = :dongCode
            ORDER BY r.createdAt DESC
            """)
    List<DongReviewListResponse> findReviewsByDongCode(@Param("dongCode") String dongCode);
}
