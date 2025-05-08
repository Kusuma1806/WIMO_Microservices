package com.wimo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wimo.dto.StockVendorResponseDTO;
import com.wimo.dto.StockZoneResponseDTO;
import com.wimo.dto.Vendor;
import com.wimo.dto.Zone;
import com.wimo.exceptions.SpaceNotAvailable;
import com.wimo.exceptions.StockItemNotFound;
import com.wimo.feignclient.VendorClient;
import com.wimo.feignclient.ZoneClient;
import com.wimo.model.StockItem;
import com.wimo.repository.StockItemRepository;

@Service
public class StockItemServiceImpl implements StockItemService {
	@Autowired
	StockItemRepository repository;

	@Autowired
	ZoneClient zoneClient;

	@Autowired
	VendorClient vendorClient;
	int UpdateCapacity;

	@Override
	public String saveStockItem(StockItem stockItem) throws SpaceNotAvailable {
		repository.save(stockItem);
		int zoneId = stockItem.getZoneId();
		Zone zone = zoneClient.viewZone(zoneId);
		UpdateCapacity = zone.getStoredCapacity() + stockItem.getStockQuantity();
		if(zone.getTotalCapacity()>UpdateCapacity) {	
		zone.setStoredCapacity(UpdateCapacity);
		zoneClient.updateZone(zone);
		}
		else {
			throw new SpaceNotAvailable("Space not available to store the stock!!!!");
		}
		return "StockItem Saved!!!";
	}

	@Override
	public StockItem updateStockItemForInbound(StockItem stockItem) {
		int zoneId = stockItem.getZoneId();
		Zone zone = zoneClient.viewZone(zoneId);
		UpdateCapacity = zone.getStoredCapacity() + stockItem.getStockQuantity();
		zone.setStoredCapacity(UpdateCapacity);
		zoneClient.updateZone(zone);
		return repository.save(stockItem);
	}
	@Override
	public StockItem updateStockItemForOutbound(StockItem stockItem) {
		int zoneId = stockItem.getZoneId();
		Zone zone = zoneClient.viewZone(zoneId);
		UpdateCapacity = zone.getStoredCapacity()-stockItem.getStockQuantity();
		zone.setStoredCapacity(UpdateCapacity);
		zoneClient.updateZone(zone);
		return repository.save(stockItem);
	}

	@Override
	public String removeStockItem(int stockId) throws StockItemNotFound {
		StockItem stockItem=repository.findById(stockId).get();
		if(stockItem==null) {
			throw new StockItemNotFound("Stock Item not found");
		}
		int zoneId = stockItem.getZoneId();
		Zone zone = zoneClient.viewZone(zoneId);
		UpdateCapacity = zone.getStoredCapacity() - stockItem.getStockQuantity();
		zone.setStoredCapacity(UpdateCapacity);
		zoneClient.updateZone(zone);
		repository.deleteById(stockId);
		return "StockItem Deleted and updated the zone capacity!!!";
	}

	@Override
	public StockItem getStockItemById(int stockId) throws StockItemNotFound {
		Optional<StockItem> optional = repository.findById(stockId);
		if (optional.isPresent())
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
	public StockZoneResponseDTO findByZoneIdIs(int zoneId) {
		Zone zone = zoneClient.viewZone(zoneId);
		List<StockItem> stocks = repository.findByZoneIdIs(zoneId);
		StockZoneResponseDTO responseDTO = new StockZoneResponseDTO(zone, stocks);
		return responseDTO;
	}

	@Override
	public StockVendorResponseDTO findByVendorIdIs(int vendorId) {
		Vendor vendor = vendorClient.viewVendor(vendorId);
		List<StockItem> stocks = repository.findByVendorIdIs(vendorId);
		StockVendorResponseDTO responseDTO = new StockVendorResponseDTO(vendor, stocks);
		return responseDTO;
	}


}
