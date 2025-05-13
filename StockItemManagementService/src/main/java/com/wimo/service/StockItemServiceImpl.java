package com.wimo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wimo.dto.StockVendorResponseDTO;
import com.wimo.dto.StockZoneResponseDTO;
import com.wimo.dto.Vendor;
import com.wimo.dto.Zone;
import com.wimo.exceptions.SpaceNotAvailable;
import com.wimo.exceptions.StockItemNotFound;
import com.wimo.exceptions.ZoneNotFound;
import com.wimo.feignclient.VendorClient;
import com.wimo.feignclient.ZoneClient;
import com.wimo.model.StockItem;
import com.wimo.repository.StockItemRepository;

@Service
//@AllArgsConstructor
public class StockItemServiceImpl implements StockItemService {
    private static final Logger logger = LoggerFactory.getLogger(StockItemServiceImpl.class);
    @Autowired
    StockItemRepository repository;
    @Autowired
    ZoneClient zoneClient;
    @Autowired
    VendorClient vendorClient;
    int updateCapacity;

    /**
     * Method to save a stock item and update the zone capacity.
     * 
     * @param stockItem the stock item to be saved
     * @return a success message
     * @throws SpaceNotAvailable if there is not enough space to store the stock
     * @throws ZoneNotFound 
     */
    @Override
    public String saveStockItem(StockItem stockItem) throws SpaceNotAvailable, ZoneNotFound {
        logger.info("Saving stock item: {}", stockItem);
        repository.save(stockItem); 
        logger.info("Stock item saved successfully: {}", stockItem);

        int zoneId = stockItem.getZoneId();
        Zone zone;
        try {
            zone = zoneClient.viewZone(zoneId);
        } catch (RuntimeException e) {
            logger.error("Zone ID not found: {}", zoneId);
            throw new ZoneNotFound("Zone ID not found");
        }
        updateCapacity = zone.getStoredCapacity() + stockItem.getStockQuantity();
        if (zone.getTotalCapacity() > updateCapacity) {
            zone.setStoredCapacity(updateCapacity);
            zoneClient.updateZone(zone);
            logger.info("Zone capacity updated successfully: {}", zone);
        } else {
            logger.error("Space not available to store the stock: {}", stockItem);
            throw new SpaceNotAvailable("Space not available to store the stock!!!!");
        }

        return "StockItem Saved!!!";
    }

    /**
     * Method to update a stock item for inbound transactions and update the zone capacity.
     * 
     * @param stockItem the stock item to be updated
     * @return the updated stock item
     * @throws ZoneNotFound 
     * @throws SpaceNotAvailable 
     */
    @Override
    public StockItem updateStockItemForInbound(StockItem stockItem) throws ZoneNotFound, SpaceNotAvailable {
        logger.info("Updating stock item for inbound: {}", stockItem);
        int zoneId = stockItem.getZoneId();
        Zone zone;
        try {
            zone = zoneClient.viewZone(zoneId);
        } catch (RuntimeException e) {
            logger.error("Zone ID not found: {}", zoneId);
            throw new ZoneNotFound("Zone ID not found");
        }
        updateCapacity = zone.getStoredCapacity() + stockItem.getStockQuantity();
        if (zone.getTotalCapacity() > updateCapacity) {
            zone.setStoredCapacity(updateCapacity);
            zoneClient.updateZone(zone);
            logger.info("Zone capacity updated successfully: {}", zone);
        } else {
            logger.error("Space not available to store the stock: {}", stockItem);
            throw new SpaceNotAvailable("Space not available to store the stock!!!!");
        }
        zone.setStoredCapacity(updateCapacity);
        zoneClient.updateZone(zone);
        logger.info("Zone capacity updated successfully for inbound: {}", zone);

        StockItem updatedStockItem = repository.save(stockItem);
        logger.info("Stock item updated successfully for inbound: {}", updatedStockItem);
        return updatedStockItem;
    }

    /**
     * Method to update a stock item for outbound transactions and update the zone capacity.
     * 
     * @param stockItem the stock item to be updated
     * @return the updated stock item
     * @throws ZoneNotFound 
     */
    @Override
    public StockItem updateStockItemForOutbound(StockItem stockItem) throws ZoneNotFound {
        logger.info("Updating stock item for outbound: {}", stockItem);
        int zoneId = stockItem.getZoneId();
        Zone zone;
        try {
            zone = zoneClient.viewZone(zoneId);
        } catch (RuntimeException e) {
            logger.error("Zone ID not found: {}", zoneId);
            throw new ZoneNotFound("Zone ID not found");
        }
        updateCapacity = zone.getStoredCapacity() - stockItem.getStockQuantity();
        zone.setStoredCapacity(updateCapacity);
        zoneClient.updateZone(zone);
        logger.info("Zone capacity updated successfully for outbound: {}", zone);

        StockItem updatedStockItem = repository.save(stockItem);
        logger.info("Stock item updated successfully for outbound: {}", updatedStockItem);
        return updatedStockItem;
    }

    /**
     * Method to remove a stock item and update the zone capacity.
     * 
     * @param stockId the ID of the stock item to be removed
     * @return a success message
     * @throws StockItemNotFound if the stock item is not found
     */
    @Override
    public String removeStockItem(int stockId) throws StockItemNotFound {
        logger.info("Removing stock item with ID: {}", stockId);
        StockItem stockItem = repository.findById(stockId).get();

        if (stockItem == null) {
            logger.error("Stock item not found with ID: {}", stockId);
            throw new StockItemNotFound("Stock Item not found");
        }

        int zoneId = stockItem.getZoneId();
        Zone zone = zoneClient.viewZone(zoneId);
        updateCapacity = zone.getStoredCapacity() - stockItem.getStockQuantity();
        zone.setStoredCapacity(updateCapacity);
        zoneClient.updateZone(zone);
        logger.info("Zone capacity updated successfully after removing stock item: {}", zone);

        repository.deleteById(stockId);
        logger.info("Stock item removed successfully: {}", stockId);

        return "StockItem Deleted and updated the zone capacity!!!";
    }

    /**
     * Method to retrieve a stock item by its ID.
     * 
     * @param stockId the ID of the stock item to be retrieved
     * @return the retrieved stock item
     * @throws StockItemNotFound if the stock item is not found
     */
    @Override
    public StockItem getStockItemById(int stockId) throws StockItemNotFound {
        logger.info("Retrieving stock item with ID: {}", stockId);
        Optional<StockItem> optional = repository.findById(stockId);

        if (optional.isPresent()) {
            logger.info("Stock item found: {}", optional.get());
            return optional.get();
        } else {
            logger.error("Stock item not found with ID: {}", stockId);
            throw new StockItemNotFound("Stock is not Found........");
        }
    }

    /**
     * Method to retrieve all stock items.
     * 
     * @return a list of all stock items
     */
    @Override
    public List<StockItem> getAllStockItems() {
        logger.info("Retrieving all stock items");
        List<StockItem> stockItems = repository.findAll();
        logger.info("All stock items retrieved successfully");
        return stockItems;
    }

    /**
     * Method to retrieve stock items by their category.
     * 
     * @param stockCategory the category of the stock items to be retrieved
     * @return a list of stock items matching the specified category
     */
    @Override
    public List<StockItem> findByStockCategoryIs(String stockCategory) {
        logger.info("Retrieving stock items by category: {}", stockCategory);
        List<StockItem> stockItems = repository.findByStockCategoryIs(stockCategory);
        logger.info("Stock items retrieved successfully by category: {}", stockCategory);
        return stockItems;
    }

    /**
     * Method to retrieve zone details and stock items by zone ID.
     * 
     * @param zoneId the ID of the zone to be retrieved
     * @return a response DTO containing the zone details and stock items
     * @throws ZoneNotFound 
     */
    @Override
    public StockZoneResponseDTO findByZoneIdIs(int zoneId) throws ZoneNotFound  {
        logger.info("Retrieving zone and stock items by zone ID: {}", zoneId);
        Zone zone;
        try {
            zone = zoneClient.viewZone(zoneId);
        } catch (RuntimeException e) {
            logger.error("Zone ID not found: {}", zoneId);
            throw new ZoneNotFound("Zone ID not found");
        }
        List<StockItem> stocks = repository.findByZoneIdIs(zoneId);
        StockZoneResponseDTO responseDTO = new StockZoneResponseDTO(zone, stocks);
        logger.info("Zone and stock items retrieved successfully by zone ID: {}", zoneId);
        return responseDTO;
    }

    /**
     * Method to retrieve vendor details and stock items by vendor ID.
     * 
     * @param vendorId the ID of the vendor to be retrieved
     * @return a response DTO containing the vendor details and stock items
     */
    @Override
    public StockVendorResponseDTO findByVendorIdIs(int vendorId) {
        logger.info("Retrieving vendor and stock items by vendor ID: {}", vendorId);
        Vendor vendor = vendorClient.viewVendor(vendorId);
        List<StockItem> stocks = repository.findByVendorIdIs(vendorId);
        StockVendorResponseDTO responseDTO = new StockVendorResponseDTO(vendor, stocks);
        logger.info("Vendor and stock items retrieved successfully by vendor ID: {}", vendorId);
        return responseDTO;
    }
}
