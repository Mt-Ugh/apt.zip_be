package com.aptzip.interestDeal.respository;

import com.aptzip.interestDeal.entity.InterestSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestSaleRepositiory extends JpaRepository<InterestSale, String> {

    @Query("SELECT " +
            "  i.aptNm, " +
            "  i.sidoName, " +
            "  i.gugunName, " +
            "  i.dongName, " +
            "  i.jibun, " +
            "  h.dealAmount " +
            "FROM InterestSale i " +
            "JOIN i.houseDeal h " +
            "WHERE i.user.userUuid = :userUuid")
    List<Object[]> findByUserUuid(String userUuid);

}
