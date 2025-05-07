package com.wimo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class StockItemManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockItemManagementServiceApplication.class, args);
	}

}
