package com.beer.order_forcast.model;

public class ProductSalesDTO {
    private String name;
    // 注意！！这里应当是用历史销量和历史销售额反推的历史价格，并非product table中的价格！！！
    private Integer price;
    // --------------------------------------------------------------------------------
    private Integer unit;
    private Integer revenue;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice() {
        if (this.unit != null && this.unit != 0 && this.revenue != null) {
            this.price = this.revenue / this.unit;
        } else {
            this.price = 0; // 或者设置为 null，看前端是否容忍
        }
    }

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
