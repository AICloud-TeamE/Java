package com.beer.order_forcast.model;

import java.time.LocalDate;

public class SalesWeatherHistoryDTO {
    private LocalDate date;
    private String weather;
    private Float highestTemperature;
    private Float lowestTemperature;
    private Integer totalSales;

    private String dayOfWeek;

    public SalesWeatherHistoryDTO(LocalDate date, String weather, Float highestTemperature,
            Float lowestTemperature, Integer totalSales,String dayOfWeek) {

        this.date = date;
        this.weather = weather;
        this.highestTemperature = highestTemperature;
        this.lowestTemperature = lowestTemperature;
        this.totalSales = totalSales;

        this.dayOfWeek = dayOfWeek;

    }

    public LocalDate getDate() {
        return date;
    }

    public String getWeather() {
        return weather;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
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
