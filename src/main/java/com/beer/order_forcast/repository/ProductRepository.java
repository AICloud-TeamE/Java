package com.beer.order_forcast.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import com.beer.order_forcast.model.*;

public interface ProductRepository extends JpaRepository<Product,Integer>{
    List<Product> findByIsDeletedFalse();

}