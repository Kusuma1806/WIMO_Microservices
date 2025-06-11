package com.wimo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wimo.dto.StockItem;
import com.wimo.dto.StockUpdateQuantityDto;

@FeignClient(name="STOCKITEMMANAGEMENTSERVICE",path="/stock")
public interface StockClient {
	@GetMapping("/fetchById/{id}")
	public StockItem viewTransactionByStock(@PathVariable("id") int stockId); 
	@PutMapping("/updateOutbound")
    StockItem updateStockItemForOutbound(@RequestBody StockUpdateQuantityDto updateDto);
    @PutMapping("/updateInbound")
    StockItem updateStockItemForInbound(@RequestBody StockUpdateQuantityDto updateDto);
	@PostMapping("/save")
	public String saveStockItem(@RequestBody @Validated StockItem stockItem);
}
