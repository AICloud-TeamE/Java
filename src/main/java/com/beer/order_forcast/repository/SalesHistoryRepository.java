package com.beer.order_forcast.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.beer.order_forcast.model.*;
import java.util.*;

public interface SalesHistoryRepository extends JpaRepository<SalesHistory, Integer> {

    //取选择年月的数据库记录
    @Query("SELECT s FROM SalesHistory s WHERE YEAR(s.date) = :year AND MONTH(s.date) = :month AND s.isDeleted = false")
    List<SalesHistory> findByYearAndMonth(@Param("year") int year, @Param("month") int month);

}
