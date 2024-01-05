package com.xdclass.net.xdclass.redis.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class XdclassRedisLearningApplication {

	public static void main(String[] args) {
		SpringApplication.run(XdclassRedisLearningApplication.class, args);
	}

}
