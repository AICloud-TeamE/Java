package com.beer.order_forcast.service;

import com.beer.order_forcast.repository.*;
import com.beer.order_forcast.model.*;

import org.springframework.stereotype.Service;
import com.beer.order_forcast.repository.*;

import java.time.LocalDate;
import java.util.*;

@Service
public class SalesHistoryService {
    private final SalesHistoryRepository repository;
    private final ProductService productService;

    public SalesHistoryService(SalesHistoryRepository repository,
            ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    

    public List<SalesHistory> findAll() {
        return repository.findAll();
    }

}
