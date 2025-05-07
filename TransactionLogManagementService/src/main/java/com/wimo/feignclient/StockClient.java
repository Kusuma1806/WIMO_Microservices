package com.wimo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wimo.dto.StockItem;

@FeignClient(name="STOCKITEMMANAGEMENTSERVICE",path="/stock")
public interface StockClient {
	@GetMapping("/fetchById/{id}")
	public StockItem viewTransactionByStock(@PathVariable("id") int stockId); 
	@PutMapping("/updateInbound")
	public StockItem updateStockItemForInbound(@RequestBody @Validated StockItem stockItem);
	@PutMapping("/updateOutbound")
	public StockItem updateStockItemForOutbound(@RequestBody @Validated StockItem stockItem);
	@PostMapping("/save")
	public String saveStockItem(@RequestBody @Validated StockItem stockItem);
}
