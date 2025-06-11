package com.wimo.controller;

import java.util.List;

import org.apache.hc.core5.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wimo.dto.StockVendorResponseDTO;
import com.wimo.dto.StockZoneResponseDTO;
import com.wimo.exceptions.SpaceNotAvailable;
import com.wimo.exceptions.StockItemNotFound;
import com.wimo.exceptions.ZoneNotFound;
import com.wimo.model.StockItem;
import com.wimo.model.StockUpdateQuantityDto;
import com.wimo.service.StockItemService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("/stock")

public class StockItemController {
	@Autowired
    StockItemService service;
    
    @PostMapping("/save")
	public String saveStockItem(@RequestBody @Validated StockItem stockItem) throws SpaceNotAvailable, ZoneNotFound {
		return service.saveStockItem(stockItem);
	}

    @PutMapping("/updateInbound")
    public StockItem updateStockItemForInbound(@Valid @RequestBody StockUpdateQuantityDto updateDto)
            throws ZoneNotFound, SpaceNotAvailable, StockItemNotFound {
        return service.updateStockItemForInbound(updateDto);
    }

    @PutMapping("/updateOutbound")
    public StockItem updateStockItemForOutbound(@Valid @RequestBody StockUpdateQuantityDto updateDto)
            throws ZoneNotFound, StockItemNotFound {
        return service.updateStockItemForOutbound(updateDto);
    }
	@GetMapping("/fetchById/{id}") 
	public StockItem getStockItemById(@PathVariable("id") int stockId) throws StockItemNotFound{
		return service.getStockItemById(stockId);
	}
	@DeleteMapping("/deleteById/{id}") 
	public String removeStockItem(@PathVariable("id") int stockId) throws StockItemNotFound {
		return service.removeStockItem(stockId);
	}
	@GetMapping("/fetchAll")
	public List<StockItem> getAllStockItems() {
		return service.getAllStockItems();
	}
	@GetMapping("/fetchByCategory/{category}") 
	public List<StockItem> findByStockCategoryIs(@PathVariable("category") String stockCategory){
		return service.findByStockCategoryIs(stockCategory);
	}
	@GetMapping("/fetchByZoneId/{zid}") 
	public StockZoneResponseDTO findByZoneIdIs(@PathVariable("zid") int zoneId) throws ZoneNotFound{
		return service.findByZoneIdIs(zoneId);
	}
	@GetMapping("/fetchByVendorId/{vid}")
	public StockVendorResponseDTO findByVendorIdIs(@PathVariable("vid") int vendorId){
		return service.findByVendorIdIs(vendorId);
	}

}
