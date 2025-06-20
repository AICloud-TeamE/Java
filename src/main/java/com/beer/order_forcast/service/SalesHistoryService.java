package com.beer.order_forcast.service;

import com.beer.order_forcast.repository.*;
import com.beer.order_forcast.model.*;

import org.springframework.stereotype.Service;
import com.beer.order_forcast.repository.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesHistoryService {
    private final SalesHistoryRepository repository;
    private final ProductRepository productRepository;
    private final WeatherHistoryRepository weatherHistoryRepository;
    private final WeatherRepository weatherRepository;

    public SalesHistoryService(
            SalesHistoryRepository repository,
            ProductRepository productRepository,
            WeatherHistoryRepository weatherHistoryRepository,
            WeatherRepository weatherRepository) {
        this.repository = repository;
        this.productRepository = productRepository;
        this.weatherHistoryRepository = weatherHistoryRepository;
        this.weatherRepository = weatherRepository;
    }

    public List<SalesHistory> findAll() {
        return repository.findAll();
    }

    public List<SalesHistory> getByYearAndMonth(int year, int month) {
        return repository.findByYearAndMonth(year, month);
    }

    // 下面是一个超~~~~~~~~~~~~~~~~~~~~~级长的dto构造
    public List<SalesWeatherHistoryDTO> getSalesWeatherByMonth(int year, int month) {

        // 最终返回用的DTO列表
        List<SalesWeatherHistoryDTO> dtoList = new ArrayList<>();

        // ① 先从history表取出指定年月的所有记录
        List<WeatherHistory> weatherHistories = weatherHistoryRepository.findByYearAndMonth(year, month);
        List<SalesHistory> salesHistories = repository.findByYearAndMonth(year, month);

        // ③ 遍历 weatherHistories，每天构建一条 DTO
        for (WeatherHistory wh : weatherHistories) {
            LocalDate date = wh.getDate();
            Float maxTemp = wh.getHighest_temperature();
            Float minTemp = wh.getLowest_temperature();

            // 查天气名称
            String weatherName = "";
            Optional<Weather> optionalWeather = weatherRepository.findById(wh.getWeather_id());
            if (optionalWeather.isPresent()) {
                weatherName = optionalWeather.get().getWeather();
            }
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            String weekdayName = getJapaneseShortWeekday(dayOfWeek);
            int weekdayNumber = dayOfWeek.getValue();

            if (weekdayNumber == 7){
                break;
            }

            // 累加该日所有销售额
            int totalSales = 0;
            for (SalesHistory sh : salesHistories) {
                if (sh.getDate().equals(date)) {
                    totalSales += sh.getSales_revenue();
                }
            }

            // DTO拼装
            SalesWeatherHistoryDTO dto = new SalesWeatherHistoryDTO(
                    date,
                    weatherName,
                    maxTemp,
                    minTemp,
                    totalSales,
                    weekdayName);

            dtoList.add(dto);
        }

        return dtoList;

    }

    // 👆上に呼び出されます
    public String getJapaneseShortWeekday(DayOfWeek dow) {
        switch (dow) {
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
    }

    public void save(SalesHistory salesHistory) {
        repository.save(salesHistory);
    }

}
