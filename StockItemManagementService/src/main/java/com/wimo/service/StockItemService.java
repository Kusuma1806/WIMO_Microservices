package com.wimo.service;

import java.util.List;

import com.wimo.dto.StockVendorResponseDTO;
import com.wimo.dto.StockZoneResponseDTO;
import com.wimo.exceptions.SpaceNotAvailable;
import com.wimo.exceptions.StockItemNotFound;
import com.wimo.exceptions.ZoneNotFound;
import com.wimo.model.StockItem;

public interface StockItemService {
	public abstract String saveStockItem(StockItem stockItem) throws SpaceNotAvailable, ZoneNotFound;

	public abstract StockItem updateStockItemForInbound(StockItem stockItem) throws ZoneNotFound, SpaceNotAvailable;

	public abstract String removeStockItem(int stockId) throws StockItemNotFound;

	public abstract StockItem getStockItemById(int stockId) throws StockItemNotFound;

	public abstract List<StockItem> getAllStockItems();
	
	public abstract List<StockItem> findByStockCategoryIs(String stockCategory);
	
	public abstract StockZoneResponseDTO findByZoneIdIs(int zoneId) throws ZoneNotFound;

	public abstract StockVendorResponseDTO findByVendorIdIs(int vendorId);

	StockItem updateStockItemForOutbound(StockItem stockItem) throws ZoneNotFound;

	

}
