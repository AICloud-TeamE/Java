package com.beer.order_forcast;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.beer.order_forcast.service.WeatherHistoryService;

import java.util.HashMap;
import java.util.Map;

import com.beer.order_forcast.service.*;

@RestController
public class FunctionTestController {

    private final WeatherHistoryService weatherHistoryService;

    public FunctionTestController(WeatherHistoryService weatherHistoryService) {
        this.weatherHistoryService = weatherHistoryService;
    }

    @GetMapping("/test-function")

    public ResponseEntity<String> callAzureFunction() {

        String functionUrl = "";

        // request body
        Map<String, String> request = new HashMap<>();
        request.put("date", "2025-06-20");

        // header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // request作成
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        //
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                functionUrl,
                HttpMethod.POST,
                entity,
                String.class);

        return response; // 打印对方返回的数据
    }

    // 定时取得天气手动方法
    @GetMapping("/test-weather-fetch")
    @ResponseBody
    public String testFetchWeather() {

        weatherHistoryService.fetchAndSaveYesterdayWeather(); // 直接调用 service 的测试方法
        return "テスト完了（天気情報取得＆保存）";
    }
}
