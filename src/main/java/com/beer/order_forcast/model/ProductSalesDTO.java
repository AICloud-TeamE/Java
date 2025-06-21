package com.beer.order_forcast.model;

public class ProductSalesDTO {
    private String name;
    // private Integer price;
    private Integer unit;
    private Integer revenue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public Integer getPrice() {
    //     return price;
    // }

    // public void setPrice(Integer price) {
    //     this.price = price;
    // }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

}
