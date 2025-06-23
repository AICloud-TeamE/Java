package com.beer.order_forcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 启用定时任务功能
public class OrderForcastApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderForcastApplication.class, args);
	}
}
