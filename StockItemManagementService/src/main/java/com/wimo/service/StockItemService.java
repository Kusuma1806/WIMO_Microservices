package com.wimo.service;

import java.util.List;

import com.wimo.model.StockItem;

public interface StockItemService {
	public abstract String saveStockItem(StockItem stockItem);

	public abstract StockItem updateStockItem(StockItem stockItem);

	public abstract String removeStockItem(int stockId);

	public abstract StockItem getStockItemById(int stockId);

	public abstract List<StockItem> getAllStockItems();
	
	public abstract List<StockItem> findByStockCategoryIs(String stockCategory);
	
	public abstract List<StockItem> findByZoneIdIs(String zoneId);
	

}
