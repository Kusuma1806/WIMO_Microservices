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
//@AllArgsConstructor
public class ZoneServiceImpl implements ZoneService {

    @Autowired
    ZoneRepository repository;

   @Autowired
    StockClient stockClient;
    
    Logger logger = LoggerFactory.getLogger(ZoneServiceImpl.class);

    /**
     * Save the zone to the repository and return a success message.
     */
    @Override
    public String saveZone(Zone zone) {
        logger.info("Saving zone");
        repository.save(zone);
        logger.info("Zone saved successfully");
        return "Zone Saved!!!";
    }

    /**
     * Update the zone in the repository.
     */
    @Override
    public Zone updateZone(Zone zone) {
        logger.info("Updating zone");
        return repository.save(zone);
    }

    /**
     * Remove the zone and all stock items in it using the zone ID.
     */
    @Override
    public String removeZone(int zoneId) {
        if(repository.existsById(zoneId)) {
            logger.info("Removing zone with ID");
            StockZoneResponseDTO responseDTO = stockClient.findByZoneIdIs(zoneId);
            List<StockItem> stocks = responseDTO.getStock();

            for (StockItem stock : stocks) {
                logger.info("Removing stock item: {}", stock);
                stockClient.removeStockItem(stock.getStockId());
            }
            logger.info("Zone and all stocks in it removed successfully: {}", zoneId);
            repository.deleteById(zoneId);
            return "Zone and all the stocks in that are Deleted!!!";
        } else {
            return "Zone Not Found";
        }
    }

    /**
     * Retrieve the zone using the user ID.
     * @throws ZoneNotFound if the zone is not found.
     */
    @Override
    public Zone getZoneById(int zoneId) throws ZoneNotFound {
        logger.info("Retrieving zone with ID: {}", zoneId);
        Optional<Zone> optional = repository.findById(zoneId);

        if (optional.isPresent()) {
            return optional.get();
        } else {
            logger.error("Zone not found with ID: {}", zoneId);
            throw new ZoneNotFound("Zone is not Found........");
        }
    }

    /**
     * Retrieve all zones from the repository.
     */
    @Override
    public List<Zone> getAllZones() {
        logger.info("Retrieving all zones");
        return repository.findAll();
    }
}
