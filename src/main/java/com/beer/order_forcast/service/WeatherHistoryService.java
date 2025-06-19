package com.beer.order_forcast.service;

import org.springframework.stereotype.Service;
import com.beer.order_forcast.repository.*;
import java.util.*;

@Service
public class WeatherHistoryService {
    private final WeatherHistoryRepository repository;

    public WeatherHistoryService(WeatherHistoryRepository repository){
        this.repository = repository;
    }

}
