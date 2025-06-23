package com.beer.order_forcast.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.beer.order_forcast.model.ForecastDTO;
import com.beer.order_forcast.repository.WeatherHistoryRepository;
import com.beer.order_forcast.repository.WeatherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@Service
public class ForecastService {
    // public List<List<Integer>> getPredictDemand() {
    // // ここ最終的にはpython function呼び出して 何とか予測のデータを取得
    // List<List<Integer>> listsquare = new ArrayList<>();
    // return listsquare;
    // }

    public List<ForecastDTO> callAzureFunction(LocalDate date) {
        String functionUrl = ""; // 替换为实际地址

        Map<String, String> request = new HashMap<>();
        request.put("date", date.toString()); // 动态传入日期

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                functionUrl,
                HttpMethod.POST,
                entity,
                String.class);

        String json = response.getBody();

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> rawList;
        try {
            rawList = objectMapper.readValue(json, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("JSON解析失败: " + e.getMessage());
        }

        List<ForecastDTO> dtoList = new ArrayList<>();

        Map<String, String> beerNameMap = Map.of(
                "pale_ale_bottles", "ペールエール",
                "lager_bottles", "ラガー",
                "ipa_bottles", "IPA",
                "white_beer_bottles", "ホワイトビール",
                "dark_beer_bottles", "黒ビール",
                "fruit_beer_bottles", "フルーツビール");

        for (Map<String, Object> item : rawList) {
            ForecastDTO dto = new ForecastDTO();

            // 日期
            dto.setDate(LocalDate.parse(item.get("date").toString()));

            // 天气
            dto.setWeather(item.get("weather").toString());

            // 平均气温 ±2 组成温度范围
            // float mean =
            // Float.parseFloat(item.get("apparent_temperature_mean").toString());
            // int low = Math.round(mean - 2);
            // int high = Math.round(mean + 2);
            // dto.setTemperatureRange(low + "〜" + high + "℃");

            float max = Float.parseFloat(item.get("temperature_2m_max").toString());
            float min = Float.parseFloat(item.get("temperature_2m_min").toString());
            dto.setTemperatureRange(Math.round(min) + "〜" + Math.round(max) + "℃");

            // 预测销量映射
            Map<String, Integer> demandMap = new LinkedHashMap<>();
            for (String key : beerNameMap.keySet()) {
                if (item.containsKey(key)) {
                    demandMap.put(beerNameMap.get(key), Integer.parseInt(item.get(key).toString()));
                }
            }
            dto.setPredictedDemandMap(demandMap);

            dtoList.add(dto);
        }

        return dtoList;
    }

    public LocalDate getNextOrderDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        switch (dayOfWeek) {

            case TUESDAY:
            case WEDNESDAY:
                return date.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
            case MONDAY:
            case THURSDAY:
                return date;

            case FRIDAY:
            case SATURDAY:
            case SUNDAY:
            default:
                return date.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
        }
    }

    
    

}
