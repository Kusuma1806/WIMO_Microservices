package com.wimo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PerformanceMetricsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PerformanceMetricsServiceApplication.class, args);
	}

}
