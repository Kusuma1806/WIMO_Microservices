package com.wimo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TransactionLogManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TransactionLogManagementServiceApplication.class, args);
	}

}
