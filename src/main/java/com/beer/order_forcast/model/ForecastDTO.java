package com.beer.order_forcast.model;

import java.time.LocalDate;
import java.util.Map;

public class ForecastDTO {
    private LocalDate date;
    private String weather;
    private String temperatureRange;
    private Map<String, Integer> predictedDemandMap;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTemperatureRange() {
        return temperatureRange;
    }

    public void setTemperatureRange(String temperatureRange) {
        this.temperatureRange = temperatureRange;
    }

    public Map<String, Integer> getPredictedDemandMap() {
        return predictedDemandMap;
    }

    public void setPredictedDemandMap(Map<String, Integer> predictedDemandMap) {
        this.predictedDemandMap = predictedDemandMap;
    }

}
