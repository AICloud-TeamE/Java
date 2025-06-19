package com.beer.order_forcast.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.beer.order_forcast.model.*;

public interface SalesHistoryRepository extends JpaRepository<SalesHistory,Integer>{
    
}
