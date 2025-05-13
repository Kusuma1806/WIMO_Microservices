package com.wimo.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.wimo.dto.TransactionLog;
import com.wimo.dto.Zone;
import com.wimo.feignclient.TransactionLogClient;
import com.wimo.feignclient.ZoneClient;
import com.wimo.model.PerformanceMetrics;
import com.wimo.repository.PerformanceMetricsRepository;

@Service
public class PerformanceMetricsServiceImpl implements PerformanceMetricsService {
   @Autowired
    PerformanceMetricsRepository repository;

   @Autowired
    ZoneClient zoneClient;

   @Autowired
    TransactionLogClient transactionClient;

    Logger log = LoggerFactory.getLogger(PerformanceMetricsServiceImpl.class);

    /**
     and save performance metrics at midnight every day.
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void scheduleMetricsCalculation() {
        calculateAndSaveMetrics();
    }

    /**
     * Method to calculate and save performance metrics such as inventory turnover rate and space utilization.
     */
    @Override
    public void calculateAndSaveMetrics() {
        log.info("Calculating and saving the metrics....! ");
        double turnoverRate = calculateInventoryTurnoverRate();
        PerformanceMetrics turnoverMetric = new PerformanceMetrics();
        turnoverMetric.setType("Turnover");
        turnoverMetric.setValue(turnoverRate);
        repository.save(turnoverMetric);

        double spaceUtilization = calculateSpaceUtilization();
        PerformanceMetrics spaceUtilizationMetric = new PerformanceMetrics();
        spaceUtilizationMetric.setType("Space Utilization");
        spaceUtilizationMetric.setValue(spaceUtilization);
        repository.save(spaceUtilizationMetric);
    }

    /**
     * Method to calculate the inventory turnover rate based on transactions within the last 30 days.
     * 
     * @return the calculated inventory turnover rate
     */
    private double calculateInventoryTurnoverRate() {
        LocalDateTime startDate = LocalDateTime.now().minusDays(30);
        LocalDateTime endDate = LocalDateTime.now();
        List<TransactionLog> outboundTransactions = transactionClient.findByTimestampBetween(startDate, endDate);

        int totalSales = outboundTransactions.stream().mapToInt(TransactionLog::getQuantity).sum();
        List<TransactionLog> allTransactions = transactionClient.findByTimestampBetween(startDate, endDate);

        double totalInventory = 0.0;
        int count = 0;
        for (TransactionLog transaction : allTransactions) {
            if (transaction.getType().equalsIgnoreCase("INBOUND")) {
                totalInventory += transaction.getQuantity();
            } else if (transaction.getType().equalsIgnoreCase("OUTBOUND")) {
                totalInventory -= transaction.getQuantity();
            }
            count++;
        }
        double averageInventory = count > 0 ? totalInventory / count : 0.0;

        return averageInventory > 0 ? (totalSales / averageInventory) : 0.0;
    }

    /**
     * Method to calculate space utilization based on the total capacity and used space in all zones.
     * 
     * @return the calculated space utilization percentage
     */
    private double calculateSpaceUtilization() {
        List<Zone> allZones = zoneClient.viewAll();
        int totalCapacity = allZones.stream().mapToInt(Zone::getTotalCapacity).sum();
        int usedSpace = allZones.stream().mapToInt(Zone::getStoredCapacity).sum();

        return totalCapacity > 0 ? ((double) usedSpace / totalCapacity * 100) : 0.0;
    }

    /**
     * Method to retrieve performance metrics based on the specified type.
     * 
     * @param type the type of performance metrics to retrieve
     * @return a list of performance metrics matching the specified type
     */
    @Override
    public List<PerformanceMetrics> findByType(String type) {
        log.info("Getting the Metrics based on the type ----turnover /space utilization");
        return repository.findByTypeIs(type);
    }
}
