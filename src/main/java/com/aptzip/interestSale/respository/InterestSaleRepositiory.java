package com.aptzip.interestSale.respository;

import com.aptzip.dealMap.entity.HouseDeal;
import com.aptzip.interestSale.entity.InterestSale;
import com.aptzip.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterestSaleRepositiory extends JpaRepository<InterestSale, String> {

    @Query("SELECT " +
            "  i.saleUuid, "+
            "  i.dongCode, "+
            "  i.houseDeal.no, "+
            "  h.houseInfo.aptSeq ," +
            "  i.aptNm, " +
            "  i.sidoName, " +
            "  i.gugunName, " +
            "  i.dongName, " +
            "  i.jibun, " +
            "  i.houseDeal.dealYear, "+
            "  i.houseDeal.dealMonth,"+
            "  i.houseDeal.dealDay,"+
            "  h.dealAmount, " +
            "  i.latitude,  " +
            "  i.longitude " +
            "FROM InterestSale i " +
            "JOIN i.houseDeal h " +
            "WHERE i.user.userUuid = :userUuid")
    List<Object[]> findByUserUuid(String userUuid);

    Optional<InterestSale> findByUserAndHouseDeal(User user, HouseDeal houseDeal);

    void deleteByUser(User user);
}
