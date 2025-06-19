package com.beer.order_forcast.model;

import jakarta.persistence.*;

@Entity
@Table(name = "weather")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "weather")
    private String weather;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public String getWeather() {
        return weather;
    }

    public Boolean getIs_deleted() {
        return isDeleted;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.isDeleted = is_deleted;
    }

    
}
