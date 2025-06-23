package com.beer.order_forcast.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.beer.order_forcast.model.*;
import java.util.*;
import java.time.*;

public interface WeatherHistoryRepository extends JpaRepository<WeatherHistory,Integer>{
    @Query("SELECT s FROM WeatherHistory s WHERE YEAR(s.date) = :year AND MONTH(s.date) = :month AND s.isDeleted = false")
    List<WeatherHistory> findByYearAndMonth(@Param("year") int year, @Param("month") int month);
    List<WeatherHistory> findByDateBetween(LocalDate startDate,LocalDate endDate);
    WeatherHistory findByDate(LocalDate date);
}
