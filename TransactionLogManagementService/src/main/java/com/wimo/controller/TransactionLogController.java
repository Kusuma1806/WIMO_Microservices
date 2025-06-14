package com.wimo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wimo.dto.TransactionStockResponseDTO;
import com.wimo.dto.TransactionUserResponseDTO;
import com.wimo.exceptions.SpaceNotAvailable;
import com.wimo.exceptions.StockItemNotFound;
import com.wimo.exceptions.TransactionLogNotFound;
import com.wimo.model.TransactionLog;
import com.wimo.service.TransactionLogService;


@RestController
@RequestMapping("/transactionlog")
//@AllArgsConstructor
public class TransactionLogController {
	@Autowired
	TransactionLogService service;

	@PostMapping("/save")
	public String recordTransactionLog(@RequestBody TransactionLog transactionLog) throws StockItemNotFound, SpaceNotAvailable {
		return service.recordTransactionLog(transactionLog);
	}

	@GetMapping("/fetchById/{id}") 
	public TransactionLog getTransactionLogById(@PathVariable("id") int transactionLog) throws TransactionLogNotFound {
		return service.getTransactionLogById(transactionLog);
	}

	@DeleteMapping("/delete/{id}")
	public String deleteTransactionLog(@PathVariable("id") int transactionLog) {
		return service.deleteTransactionLog(transactionLog);

	}

	@GetMapping("/fetchByType/{TransactionType}")
	public List<TransactionLog> getTransactionLogsByType(@PathVariable("TransactionType") String type) {
		return service.getTransactionLogsByType(type);
	}

	@GetMapping("/fetchAll")
	public List<TransactionLog> getAllTransactions() {
		return service.getAllTransactionLogs();
	}

	@GetMapping("/fetchByStock/{stock}")
	public TransactionStockResponseDTO getTransactionLogsByZone(@PathVariable("stock") int stockId) {
		return service.getTransactionLogsByStock(stockId);

	}
	
	@GetMapping("/fetchByUser/{user}")
	public TransactionUserResponseDTO getTransactionLogsByUser(@PathVariable("user") int userId) {
		return service.getTransactionLogsByUser(userId);

	}

	
	@GetMapping("/fetchByDateTimestamp/{start}/{end}")
	public List<TransactionLog> getTransactionLogsByTimestampBetween(@PathVariable("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  LocalDate startDate,
			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)  @PathVariable("end") LocalDate endDate) {
		//convert LocalDate to LocalDateTime
		LocalDateTime startDateTime = startDate.atStartOfDay();
		LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
		return service.getTransactionLogsByTimestampBetween(startDateTime, endDateTime);
	}
	@GetMapping("/fetchByTimestamp/{start}/{end}")
	public List<TransactionLog> findByTimestampBetween(@PathVariable("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  LocalDateTime startDate,
			 @PathVariable("end") @DateTimeFormat(iso= DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
		return service.getTransactionLogsByTimestampBetween(startDate, endDate);
	}

	@GetMapping("/fetchByPrice/{initial}/{final}")
	public List<TransactionLog> getTransactionLogsByPriceBetween(@PathVariable("initial") Double initialPrice,
			@PathVariable("final") Double finalPrice) {
		return service.getTransactionLogsByPriceBetween(initialPrice, finalPrice);

	}

}
