package com.beer.order_forcast.model;

import jakarta.persistence.*;

import java.security.Timestamp;
import java.time.*;


@Entity
@Table(name = "sales_history")
public class SalesHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "product_id")
    private Integer product_id;

    @Column(name = "creator_id")
    private Integer creator_id;

    @Column(name = "update_id")
    private Integer update_id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "weather_history_id")
    private Integer weather_history_id;

    @Column(name = "sales_count")
    private Integer sales_count;

    @Column(name = "sales_revenue")
    private Integer sales_revenue;

    @Column(name = "created_at")
    private Timestamp created_at;

    @Column(name = "updated_at")
    private Timestamp updated_at;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public Integer getCreator_id() {
        return creator_id;
    }

    public Integer getUpdate_id() {
        return update_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public Integer getWeather_history_id() {
        return weather_history_id;
    }

    public Integer getSales_count() {
        return sales_count;
    }

    public Integer getSales_revenue() {
        return sales_revenue;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public Boolean getIs_deleted() {
        return isDeleted;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public void setCreator_id(Integer creator_id) {
        this.creator_id = creator_id;
    }

    public void setUpdate_id(Integer update_id) {
        this.update_id = update_id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setWeather_history_id(Integer weather_history_id) {
        this.weather_history_id = weather_history_id;
    }

    public void setSales_count(Integer sales_count) {
        this.sales_count = sales_count;
    }

    public void setSales_revenue(Integer sales_revenue) {
        this.sales_revenue = sales_revenue;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.isDeleted = is_deleted;
    }

    
}
