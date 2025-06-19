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
    private Integer weather_id;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "highest_temperature")
    private Float highest_temperature;

    @Column(name = "lowest_temperature")
    private Float lowest_temperature;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(Integer weather_id) {
        this.weather_id = weather_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getHighest_temperature() {
        return highest_temperature;
    }

    public void setHighest_temperature(Float highest_temperature) {
        this.highest_temperature = highest_temperature;
    }

    public Float getLowest_temperature() {
        return lowest_temperature;
    }

    public void setLowest_temperature(Float lowest_temperature) {
        this.lowest_temperature = lowest_temperature;
    }

    public Boolean getIs_deleted() {
        return isDeleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.isDeleted = is_deleted;
    }

}