package com.wimo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final Logger logger = LoggerFactory.getLogger(StockItemServiceImpl.class);

    @Autowired
    StockItemRepository repository;

    @Autowired
    ZoneClient zoneClient;

    @Autowired
    VendorClient vendorClient;
    int UpdateCapacity;

    @Override
    public String saveStockItem(StockItem stockItem) throws SpaceNotAvailable {
        logger.info("Saving stock item: {}", stockItem);
        // Save the stock item to the repository
        repository.save(stockItem);
        logger.info("Stock item saved successfully: {}", stockItem);
        
        // Get the zone ID from the stock item
        int zoneId = stockItem.getZoneId();
        
        // Retrieve the zone details using the zone ID
        Zone zone = zoneClient.viewZone(zoneId);
        
        // Calculate the updated capacity of the zone
        int UpdateCapacity = zone.getStoredCapacity() + stockItem.getStockQuantity();
        
        // Check if the updated capacity is within the total capacity of the zone
        if (zone.getTotalCapacity() > UpdateCapacity) {
            // Update the stored capacity of the zone
            zone.setStoredCapacity(UpdateCapacity);
            zoneClient.updateZone(zone);
            logger.info("Zone capacity updated successfully: {}", zone);
        } else {
            logger.error("Space not available to store the stock: {}", stockItem);
            // Throw an exception if there is not enough space to store the stock
            throw new SpaceNotAvailable("Space not available to store the stock!!!!");
        }
        
        // Return a success message
        return "StockItem Saved!!!";
    }

    @Override
    public StockItem updateStockItemForInbound(StockItem stockItem) {
        logger.info("Updating stock item for inbound: {}", stockItem);
        // Get the zone ID from the stock item
        int zoneId = stockItem.getZoneId();
        
        // Retrieve the zone details using the zone ID
        Zone zone = zoneClient.viewZone(zoneId);
        
        // Calculate the updated capacity of the zone
        int UpdateCapacity = zone.getStoredCapacity() + stockItem.getStockQuantity();
        
        // Update the stored capacity of the zone
        zone.setStoredCapacity(UpdateCapacity);
        zoneClient.updateZone(zone);
        logger.info("Zone capacity updated successfully for inbound: {}", zone);
        
        // Save the updated stock item to the repository
        StockItem updatedStockItem = repository.save(stockItem);
        logger.info("Stock item updated successfully for inbound: {}", updatedStockItem);
        return updatedStockItem;
    }

    @Override
    public StockItem updateStockItemForOutbound(StockItem stockItem) {
        logger.info("Updating stock item for outbound: {}", stockItem);
        // Get the zone ID from the stock item
        int zoneId = stockItem.getZoneId();
        
        // Retrieve the zone details using the zone ID
        Zone zone = zoneClient.viewZone(zoneId);
        
        // Calculate the updated capacity of the zone
        int UpdateCapacity = zone.getStoredCapacity() - stockItem.getStockQuantity();
        
        // Update the stored capacity of the zone
        zone.setStoredCapacity(UpdateCapacity);
        zoneClient.updateZone(zone);
        logger.info("Zone capacity updated successfully for outbound: {}", zone);
        
        // Save the updated stock item to the repository
        StockItem updatedStockItem = repository.save(stockItem);
        logger.info("Stock item updated successfully for outbound: {}", updatedStockItem);
        return updatedStockItem;
    }

    @Override
    public String removeStockItem(int stockId) throws StockItemNotFound {
        logger.info("Removing stock item with ID: {}", stockId);
        // Retrieve the stock item using the stock ID
        StockItem stockItem = repository.findById(stockId).get();
        
        // Check if the stock item exists
        if (stockItem == null) {
            logger.error("Stock item not found with ID: {}", stockId);
            throw new StockItemNotFound("Stock Item not found");
        }
        
        // Get the zone ID from the stock item
        int zoneId = stockItem.getZoneId();
        
        // Retrieve the zone details using the zone ID
        Zone zone = zoneClient.viewZone(zoneId);
        
        // Calculate the updated capacity of the zone
        int UpdateCapacity = zone.getStoredCapacity() - stockItem.getStockQuantity();
        
        // Update the stored capacity of the zone
        zone.setStoredCapacity(UpdateCapacity);
        zoneClient.updateZone(zone);
        logger.info("Zone capacity updated successfully after removing stock item: {}", zone);
        
        // Delete the stock item from the repository
        repository.deleteById(stockId);
        logger.info("Stock item removed successfully: {}", stockId);
        
        // Return a success message
        return "StockItem Deleted and updated the zone capacity!!!";
    }

    @Override
    public StockItem getStockItemById(int stockId) throws StockItemNotFound {
        logger.info("Retrieving stock item with ID: {}", stockId);
        // Retrieve the stock item using the stock ID
        Optional<StockItem> optional = repository.findById(stockId);
        
        // Check if the stock item exists
        if (optional.isPresent()) {
            logger.info("Stock item found: {}", optional.get());
            return optional.get();
        } else {
            logger.error("Stock item not found with ID: {}", stockId);
            throw new StockItemNotFound("Stock is not Found........");
        }
    }

    @Override
    public List<StockItem> getAllStockItems() {
        logger.info("Retrieving all stock items");
        // Retrieve all stock items from the repository
        List<StockItem> stockItems = repository.findAll();
        logger.info("All stock items retrieved successfully");
        return stockItems;
    }

    @Override
    public List<StockItem> findByStockCategoryIs(String stockCategory) {
        logger.info("Retrieving stock items by category: {}", stockCategory);
        // Retrieve stock items by category from the repository
        List<StockItem> stockItems = repository.findByStockCategoryIs(stockCategory);
        logger.info("Stock items retrieved successfully by category: {}", stockCategory);
        return stockItems;
    }

    @Override
    public StockZoneResponseDTO findByZoneIdIs(int zoneId) {
        logger.info("Retrieving zone and stock items by zone ID: {}", zoneId);
        // Retrieve the zone details using the zone ID
        Zone zone = zoneClient.viewZone(zoneId);
        
        // Retrieve stock items by zone ID from the repository
        List<StockItem> stocks = repository.findByZoneIdIs(zoneId);
        
        // Create a response DTO with the zone and stock items
        StockZoneResponseDTO responseDTO = new StockZoneResponseDTO(zone, stocks);
        logger.info("Zone and stock items retrieved successfully by zone ID: {}", zoneId);
        return responseDTO;
    }

    @Override
    public StockVendorResponseDTO findByVendorIdIs(int vendorId) {
        logger.info("Retrieving vendor and stock items by vendor ID: {}", vendorId);
        // Retrieve the vendor details using the vendor ID
        Vendor vendor = vendorClient.viewVendor(vendorId);
        
        // Retrieve stock items by vendor ID from the repository
        List<StockItem> stocks = repository.findByVendorIdIs(vendorId);
        
        // Create a response DTO with the vendor and stock items
        StockVendorResponseDTO responseDTO = new StockVendorResponseDTO(vendor, stocks);
        logger.info("Vendor and stock items retrieved successfully by vendor ID: {}", vendorId);
        return responseDTO;
    }
}
