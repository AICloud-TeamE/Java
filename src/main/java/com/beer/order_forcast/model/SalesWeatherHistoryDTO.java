package com.beer.order_forcast.model;

import java.time.LocalDate;

public class SalesWeatherHistoryDTO {
    private LocalDate date;
    private String weather;
    private Float highestTemperature;
    private Float lowestTemperature;
    private Integer totalSales;

    public SalesWeatherHistoryDTO(LocalDate date, String weather, Float highestTemperature,
            Float lowestTemperature, Integer totalSales) {
        this.date = date;
        this.weather = weather;
        this.highestTemperature = highestTemperature;
        this.lowestTemperature = lowestTemperature;
        this.totalSales = totalSales;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getWeather() {
        return weather;
    }

    public Float getHighestTemperature() {
        return highestTemperature;
    }

    public Float getLowestTemperature() {
        return lowestTemperature;
    }

    public Integer getTotalSales() {
        return totalSales;
    }
}
