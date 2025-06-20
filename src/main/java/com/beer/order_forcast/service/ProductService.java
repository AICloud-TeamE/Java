package com.beer.order_forcast.service;

import org.springframework.stereotype.Service;

import com.beer.order_forcast.model.Product;
import com.beer.order_forcast.model.SalesHistory;
import com.beer.order_forcast.repository.*;
import java.util.*;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> findAll() {
        return repository.findAll();
    }

    // gpt生成的整理model返回前端需要的map
    public Map<String, Integer> getActiveProductPriceMap() {
        List<Product> allProducts = repository.findAll();

        Map<String, Integer> productPriceMap = new LinkedHashMap<>();
        for (Product product : allProducts) {
            if (!Boolean.TRUE.equals(product.getIs_deleted())) {
                try {
                    int price = Integer.parseInt(product.getPrice());
                    productPriceMap.put(product.getName(), price);
                } catch (NumberFormatException e) {
                    System.err.println("価格の形式が不正です：" + product.getPrice());
                    // 必要ならログに記録やスキップ処理を追加
                }
            }
        }

        return productPriceMap;
    }

    // 崩溃 这是啥时候写的 哪用了？？？？？
    public Map<String, Integer> getValidProductMap() {
        List<Product> products = repository.findByIsDeletedFalse();
        Map<String, Integer> map = new HashMap<>();
        for (Product p : products) {
            map.put(p.getName(), Integer.parseInt(p.getPrice())); // price 是 String 的话这里 parse
        }
        return map;
    }

    // ProductService.java
    public List<Product> findAllActiveProducts() {
        return repository.findByIsDeletedFalse();
    }

    //
    public Integer forecast_sum(List<Integer> list) {

        return 0;
    }

    public Integer findPriceByProductId(Integer id) {
        return repository.findPriceByProductId(id);
    }

}
