package com.aptzip.review.repository;

import com.aptzip.review.dto.response.UserReviewResponse;
import com.aptzip.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    @Query("""
    SELECT new com.aptzip.review.dto.response.UserReviewResponse(
        r.reviewUuid,
        d.dongName,
        r.content,
        r.createdAt
    )
    FROM Review r
    JOIN r.dongCode d
    WHERE r.deletedAt IS NULL
      AND r.user.userUuid = :userUuid
    ORDER BY r.createdAt DESC
""")
    List<UserReviewResponse> findReviewsByUserUuid(@Param("userUuid") String userUuid);
}
