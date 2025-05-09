package com.wimo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wimo.dto.StockItem;
import com.wimo.dto.StockZoneResponseDTO;
import com.wimo.exceptions.ZoneNotFound;
import com.wimo.feignclient.StockClient;
import com.wimo.model.Zone;
import com.wimo.repository.ZoneRepository;

@Service
public class ZoneServiceImpl implements ZoneService {

	@Autowired
	ZoneRepository repository;

	@Autowired
	StockClient stockClient;
	
    Logger logger = LoggerFactory.getLogger(ZoneServiceImpl.class);


	@Override
	public String saveZone(Zone zone) {
		// Save the zone to the repository
		logger.info("Saving zone");
		repository.save(zone);

		// Return a success message
		logger.info("Zone saved successfully");
		return "Zone Saved!!!";
	}

	@Override
	public Zone updateZone(Zone zone) {
		logger.info("Updating zone");
		// Update the zone in the repository
		return repository.save(zone);
	}

	@Override
	public String removeZone(int zoneId) {
		logger.info("Removing zone with ID");
		// Retrieve the stock items in the zone using the zone ID
		StockZoneResponseDTO responseDTO = stockClient.findByZoneIdIs(zoneId);
		List<StockItem> stocks = responseDTO.getStock();

		// Remove each stock item in the zone
		for (StockItem stock : stocks) {
            
			logger.info("Removing stock item: {}", stock);

			stockClient.removeStockItem(stock.getStockId());
		}
		logger.info("Zone and all stocks in it removed successfully: {}", zoneId);
		// Delete the zone using the zone ID
		repository.deleteById(zoneId);

		// Return a success message
		return "Zone and all the stocks in that are Deleted!!!";
	}

	@Override
	public Zone getZoneById(int userId) throws ZoneNotFound {

        logger.info("Retrieving zone with ID: {}", userId);

		// Retrieve the zone using the user ID
		Optional<Zone> optional = repository.findById(userId);

		// Check if the zone exists
		if (optional.isPresent())
			return optional.get();
		else
			logger.error("Zone not found with ID: {}", userId);
			throw new ZoneNotFound("Zone is not Found........");
	}

	@Override
	public List<Zone> getAllZones() {
		logger.info("Retrieving all zones");
		// Retrieve all zones from the repository
		return repository.findAll();
	}

}
