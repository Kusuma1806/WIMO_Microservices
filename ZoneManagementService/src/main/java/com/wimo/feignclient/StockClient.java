package com.wimo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wimo.dto.StockZoneResponseDTO;

@FeignClient(name="STOCKITEMMANAGEMENTSERVICE",path="/stock")
public interface StockClient {
	@GetMapping("/fetchByZoneId/{zid}")
	public StockZoneResponseDTO findByZoneIdIs(@PathVariable("zid") int zoneId);
	@DeleteMapping("/deleteById/{id}")
	public String removeStockItem(@PathVariable("id") int stockId);
}
