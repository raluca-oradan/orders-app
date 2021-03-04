package com.javaAdvanced.ordersapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class OrdersAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrdersAppApplication.class, args);
	}
}
