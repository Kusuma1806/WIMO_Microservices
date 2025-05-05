package com.wimo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wimo.exceptions.StockItemNotFound;
import com.wimo.model.StockItem;
import com.wimo.repository.StockItemRepository;
@Service
public class StockItemServiceImpl implements StockItemService {
	@Autowired
	StockItemRepository repository;

	@Override
	public String saveStockItem(StockItem stockItem) {
		repository.save(stockItem);
		return "StockItem Saved!!!";
	}

	@Override
	public StockItem updateStockItem(StockItem stockItem) {
		return repository.save(stockItem);
	}

	@Override
	public String removeStockItem(int stockId) {
		repository.deleteById(stockId);
		return "StockItem Deleted!!!";
	}

	@Override
	public StockItem getStockItemById(int userId) throws StockItemNotFound{
		Optional<StockItem> optional=repository.findById(userId);
		if(optional.isPresent())
			return optional.get();
		else
			throw new StockItemNotFound("Stock is not Found........");
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
	public List<StockItem> findByZoneIdIs(int zoneId) {
		return repository.findByZoneIdIs(zoneId);
	}

}
