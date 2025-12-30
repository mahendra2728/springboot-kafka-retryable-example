package com.pm.kafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class SpringbootKafkaRetryableExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootKafkaRetryableExampleApplication.class, args);
	}

}
