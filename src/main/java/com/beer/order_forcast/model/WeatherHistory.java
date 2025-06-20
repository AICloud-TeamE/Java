package com.beer.order_forcast.model;

import jakarta.persistence.*;
import java.time.*;

@Entity
@Table(name = "weather_history")
public class WeatherHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "weather_id")
    private Integer weatherId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "highest_temperature")
    private Float highestTemperature;

    @Column(name = "lowest_temperature")
    private Float lowestTemperature;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeather_id() {
        return weatherId;
    }

    public void setWeather_id(Integer weather_id) {
        this.weatherId = weather_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getHighest_temperature() {
        return highestTemperature;
    }

    public void setHighest_temperature(Float highest_temperature) {
        this.highestTemperature = highest_temperature;
    }

    public Float getLowest_temperature() {
        return lowestTemperature;
    }

    public void setLowest_temperature(Float lowest_temperature) {
        this.lowestTemperature = lowest_temperature;
    }

    public Boolean getIs_deleted() {
        return isDeleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.isDeleted = is_deleted;
    }

}