package com.beer.order_forcast.service;

import org.springframework.dao.DataIntegrityViolationException;
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

    public List<Product> findByIsDeletedFalse() {
        return repository.findByIsDeletedFalse();
    }

    // gpt生成的整理model返回前端需要的map
    public Map<String, Integer> getActiveProductPriceMap() {
        List<Product> allProducts = repository.findAll();

        Map<String, Integer> productPriceMap = new LinkedHashMap<>();
        for (Product product : allProducts) {
            if (!Boolean.TRUE.equals(product.getIs_deleted())) {
                try {
                    int price = product.getPrice();
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
            map.put(p.getName(), p.getPrice()); // price 是 String 的话这里 parse
        }
        return map;
    }

    // ProductService.java
    public List<Product> findAllActiveProducts() {
        return repository.findByIsDeletedFalse();
    }

    public Integer findPriceByProductId(Integer id) {
        return repository.findPriceByProductId(id);
    }

    // frontendからもらった追加商品の情報をDBに入れる
    public String addProduct(String name, Integer price) {

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setIs_deleted(false);
        Product existence = repository.findByName(name);
        try{
            if(existence != null){
            if (existence.getIs_deleted() == true){
                existence.setIs_deleted(false);
                existence.setPrice(price);
                repository.save(existence);
                return "削除された商品を復元しました";
            }else{
                return "既に存在している商品です";
            }
                
        }else{
            repository.save(product);
            return "商品追加成功";
        }
        }catch(Exception e){
            return "未知エラー"+ e.getMessage();
        }
        

    }

    // edit商品
    public String editProduct(Integer id,String name,Integer price){
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isEmpty()) {
            return "指定された商品が存在しません";
        }
        try {
            Product product = optionalProduct.get();
            //logical削除
            product.setName(name); 
            product.setPrice(price); 
            
            repository.save(product); 
            return "編集成功しました";
        } catch (Exception e) {
            return "未知エラー";
        }
    }

    // logically 削除商品
    public String deleteProductById(Integer id) {
        Optional<Product> optionalProduct = repository.findById(id);

        if (optionalProduct.isEmpty()) {
            return "指定された商品が存在しません";
        }

        try {
            Product product = optionalProduct.get();
            //logical削除
            product.setIs_deleted(true); 
            repository.save(product); 
            return "削除成功しました";
        } catch (Exception e) {
            return "未知エラー";
        }
    }

}
