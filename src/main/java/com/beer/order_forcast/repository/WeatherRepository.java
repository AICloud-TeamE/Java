package com.beer.order_forcast.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beer.order_forcast.model.Weather;

public interface WeatherRepository extends JpaRepository<Weather,Integer>{
    Optional<Weather> findByWeather(String weatherName);
}
