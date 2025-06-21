package com.beer.order_forcast.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class SalesWeatherHistoryDTO {
    private LocalDate date;
    private String dayOfWeek;
    private String weather;
    private Float highestTemperature;
    private Float lowestTemperature;
    private Integer totalSales;
    private List<ProductSalesDTO> productSales;

    //追加可能機能
    private String editHistory;
    private String editor;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
        this.dayOfWeek = convertToDayOfWeek(date);
    }

    private String convertToDayOfWeek(LocalDate date) {
        try {
            
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            switch (dayOfWeek) {
                case MONDAY:
                    return "月";
                case TUESDAY:
                    return "火";
                case WEDNESDAY:
                    return "水";
                case THURSDAY:
                    return "木";
                case FRIDAY:
                    return "金";
                case SATURDAY:
                    return "土";
                case SUNDAY:
                    return "日";
                default:
                    return "";
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public Float getHighestTemperature() {
        return highestTemperature;
    }

    public void setHighestTemperature(Float highestTemperature) {
        this.highestTemperature = highestTemperature;
    }

    public Float getLowestTemperature() {
        return lowestTemperature;
    }

    public void setLowestTemperature(Float lowestTemperature) {
        this.lowestTemperature = lowestTemperature;
    }

    public Integer getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Integer totalSales) {
        this.totalSales = totalSales;
    }

    public List<ProductSalesDTO> getProductSales() {
        return productSales;
    }

    public void setProductSales(List<ProductSalesDTO> productSales) {
        this.productSales = productSales;
    }

    public String getEditHistory() {
        return editHistory;
    }

    public void setEditHistory(String editHistory) {
        this.editHistory = editHistory;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

}
