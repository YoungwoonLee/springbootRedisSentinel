package com.sentinel.engis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
//@EnableAutoConfiguration
public class RedisSentinelApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisSentinelApplication.class, args);
	}

}
