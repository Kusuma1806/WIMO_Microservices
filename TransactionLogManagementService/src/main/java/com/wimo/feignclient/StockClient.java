package com.wimo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wimo.dto.StockItem;

@FeignClient(name="STOCKITEMMANAGEMENTSERVICE",path="/stock")
public interface StockClient {
	@GetMapping("/fetchById/{id}")
	public StockItem viewTransactionByStock(@PathVariable("id") int stockId); 
	

}
