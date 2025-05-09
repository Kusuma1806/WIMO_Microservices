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
public class PerformanceMetricsServiceImpl implements PerformanceMetricsService{
	@Autowired
	PerformanceMetricsRepository repository;

	@Autowired
	ZoneClient zoneClient;

	@Autowired
	TransactionLogClient transactionClient;
	
	Logger log=LoggerFactory.getLogger(PerformanceMetricsServiceImpl.class);
	
	@Scheduled(cron="0 0 0 * * ?")//at every midnight
	public void scheduleMetricsCalculation() {
		calculateAndSaveMetrics();
	}
	//Calculating and saving the metrics....! 
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

	private double calculateInventoryTurnoverRate() {
		// Define the period for calculation (last 30 days)
		LocalDateTime startDate = LocalDateTime.now().minusDays(30);
		LocalDateTime endDate = LocalDateTime.now();
		// Fetch all outbound transactions within the period
		List<TransactionLog> outboundTransactions = transactionClient.findByTimestampBetween(startDate, endDate);

		// Calculate total sales (outbound transactions)
		int totalSales = outboundTransactions.stream().mapToInt(TransactionLog::getQuantity).sum();

		// Fetch all inbound and outbound transactions within the period
		List<TransactionLog> allTransactions = transactionClient.findByTimestampBetween(startDate, endDate);

		// Calculate average inventory
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

		// Calculate inventory turnover rate
		return averageInventory > 0 ? (totalSales / averageInventory) : 0.0;
	}

	private double calculateSpaceUtilization() {
		List<Zone> allZones = zoneClient.viewAll();
		// Calculate total capacity and used space
		int totalCapacity = allZones.stream().mapToInt(Zone::getTotalCapacity).sum();
		int usedSpace = allZones.stream().mapToInt(Zone::getStoredCapacity).sum();
		// Calculate space utilization
		return totalCapacity > 0 ? ((double)usedSpace / totalCapacity * 100) : 0.0;
	}
	//Getting the Metrics based on the type ----turnover /space utilization
	@Override
	public List<PerformanceMetrics> findByType(String type) {
		log.info("Getting the Metrics based on the type ----turnover /space utilization");;
		return repository.findByTypeIs(type);
	}
}
