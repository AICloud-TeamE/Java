package com.beer.order_forcast.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.beer.order_forcast.model.*;

public interface ProductRepository extends JpaRepository<Product,Integer>{
    List<Product> findByIsDeletedFalse();

    @Query("SELECT p.price FROM Product p WHERE p.id = :id")
    Integer findPriceByProductId(@Param("id") Integer id);

    Product findByName(String name);
}