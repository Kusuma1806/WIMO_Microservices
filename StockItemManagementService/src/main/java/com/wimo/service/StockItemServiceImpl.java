package com.wimo.service;

import java.util.List;

import com.wimo.model.StockItem;
import com.wimo.repository.StockItemRepository;

public class StockItemServiceImpl implements StockItemService {
	
	StockItemRepository repository;

	@Override
	public String saveStockItem(StockItem stockItem) {
		repository.save(stockItem);
		return "Stock Item Saved!!!";
	}

	@Override
	public StockItem updateStockItem(StockItem stockItem) {
		return repository.save(stockItem);
	}

	@Override
	public String removeStockItem(int stockId) {
		repository.deleteById(stockId);
		return "stock Item Deleted!!!";
	}

	@Override
	public StockItem getStockItemById(int stockId) {
		return repository.findById(stockId).get();
	}

	@Override
	public List<StockItem> getAllStockItems() {
		return repository.findAll();
	}

	@Override
	public List<StockItem> findByStockCategoryIs(String stockCategory) {
		return repository.findByStockCategoryIs(stockCategory);
	}

	@Override
	public List<StockItem> findByZoneIdIs(String zoneId) {
		return repository.findByZoneIdIs(zoneId);
	}

}
