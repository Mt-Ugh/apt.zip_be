package com.aptzip.dealMap.repository;


import com.aptzip.dealMap.entity.DongCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DealMapRepository extends JpaRepository<DongCode, String> {

    // 시도 이름 
    @Query("SELECT DISTINCT d.sidoName FROM DongCode d")
    List<String> findSidoName();

    // 구군 이름
    @Query("SELECT DISTINCT d.gugunName FROM DongCode d WHERE d.sidoName = :sidoName AND d.gugunName IS NOT NULL")
    List<String> findGugunNameBySidoName(String sidoName);

    //  읍면동 이름
    @Query("SELECT DISTINCT d.dongCode, d.dongName FROM DongCode d WHERE d.sidoName = :sidoName AND d.gugunName = :gugunName AND d.dongName IS NOT NULL")
    List<Object[]> findBySidoNameAndGugunName(String sidoName, String gugunName);
}