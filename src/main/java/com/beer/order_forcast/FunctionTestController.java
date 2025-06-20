package com.beer.order_forcast;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;


@RestController
public class FunctionTestController {

    @GetMapping("/test-function")



    public ResponseEntity<String> callAzureFunction() {

        String functionUrl = "";


        //request body
        Map<String, String> request = new HashMap<>();
        request.put("date", "2025-06-20");

        //header
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //request作成
        HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);

        //
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(
                functionUrl,
                HttpMethod.POST,
                entity,
                String.class
        );
        // String response = restTemplate.getForObject(functionUrl, String.class);


        return response; // 打印对方返回的数据
    }
}
