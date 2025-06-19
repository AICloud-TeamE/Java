package com.beer.order_forcast.service;

import org.springframework.stereotype.Service;
import com.beer.order_forcast.repository.*;
import java.util.*;

@Service
public class WeatherService {
    private final WeatherRepository repository;

    public WeatherService(WeatherRepository repository){
        this.repository = repository;
    }

}
