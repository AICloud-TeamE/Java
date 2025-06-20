package com.beer.order_forcast.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

import java.time.*;


@Entity
@Table(name = "sales_history")
public class SalesHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "creator_id")
    private Integer creatorId;

    @Column(name = "update_id")
    private Integer updateId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "weather_history_id")
    private Integer weatherHistoryId;

    @Column(name = "sales_count")
    private Integer salesCount;

    @Column(name = "sales_revenue")
    private Integer salesRevenue;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public Integer getProduct_id() {
        return productId;
    }

    public Integer getCreator_id() {
        return creatorId;
    }

    public Integer getUpdate_id() {
        return updateId;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getWeather_history_id() {
        return weatherHistoryId;
    }

    public Integer getSales_count() {
        return salesCount;
    }

    public Integer getSales_revenue() {
        return salesRevenue;
    }

    public Timestamp getCreated_at() {
        return createdAt;
    }

    public Timestamp getUpdated_at() {
        return updatedAt;
    }

    public Boolean getIs_deleted() {
        return isDeleted;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProduct_id(Integer product_id) {
        this.productId = product_id;
    }

    public void setCreator_id(Integer creator_id) {
        this.creatorId = creator_id;
    }

    public void setUpdate_id(Integer update_id) {
        this.updateId = update_id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setWeather_history_id(Integer weather_history_id) {
        this.weatherHistoryId = weather_history_id;
    }

    public void setSales_count(Integer sales_count) {
        this.salesCount = sales_count;
    }

    public void setSales_revenue(Integer sales_revenue) {
        this.salesRevenue = sales_revenue;
    }

    public void setCreated_at(Timestamp created_at) {
        this.createdAt = created_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updatedAt = updated_at;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.isDeleted = is_deleted;
    }

    
}
