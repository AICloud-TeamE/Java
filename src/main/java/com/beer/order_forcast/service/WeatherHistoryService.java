package com.beer.order_forcast.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;


import com.beer.order_forcast.model.Weather;
import com.beer.order_forcast.model.WeatherHistory;
import com.beer.order_forcast.repository.WeatherHistoryRepository;
import com.beer.order_forcast.repository.WeatherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class WeatherHistoryService {
    // private final WeatherHistoryRepository repository;

    // public WeatherHistoryService(WeatherHistoryRepository repository){
    // this.repository = repository;
    // }

    private final WeatherRepository weatherRepository;
    private final WeatherHistoryRepository weatherHistoryRepository;
    private final RestTemplate restTemplate;

    public WeatherHistoryService(
            WeatherRepository weatherRepository,
            WeatherHistoryRepository weatherHistoryRepository) {
        this.weatherRepository = weatherRepository;
        this.weatherHistoryRepository = weatherHistoryRepository;
        this.restTemplate = new RestTemplate();
    }


    //
    @Scheduled(cron = "0 0 19 * * *") // 每天凌晨2点执行
    public void fetchAndSaveYesterdayWeather() {
        String url = "";

        // Map<String, String> body = new HashMap<>();
        // LocalDate yesterday = LocalDate.now().minusDays(1);
        // body.put("date", yesterday.toString());

        // HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.APPLICATION_JSON);
        // HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

        // ResponseEntity<String> response = restTemplate.exchange(
        //         url, HttpMethod.POST, request, String.class);
        ResponseEntity<String> response = restTemplate.exchange(
            url, HttpMethod.GET, null, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> list = mapper.readValue(
                    response.getBody(), new TypeReference<>() {
                    });

            for (Map<String, Object> item : list) {
                WeatherHistory wh = new WeatherHistory();
                wh.setDate(LocalDate.parse(item.get("date").toString()));
                wh.setHighest_temperature(Float.parseFloat(item.get("temperature_2m_max").toString()));
                wh.setLowest_temperature(Float.parseFloat(item.get("temperature_2m_min").toString()));
                wh.setIs_deleted(false);

                // 查 weather_id
                String weatherName = item.get("weather").toString();
                Optional<Weather> weatherOpt = weatherRepository.findByWeather(weatherName);
                if (weatherOpt.isPresent()) {
                    wh.setWeather_id(weatherOpt.get().getId());
                    weatherHistoryRepository.save(wh);
                } else {
                    System.out.println("未找到对应天气：" + weatherName);
                }
            }
        } catch (Exception e) {
            System.out.println("处理天气数据失败：" + e.getMessage());
        }
    }

}
