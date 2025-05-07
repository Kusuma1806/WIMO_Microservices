package com.wimo.service;

import java.time.LocalDate;
import java.util.List;

import com.wimo.dto.TransactionStockResponseDTO;
import com.wimo.dto.TransactionUserResponseDTO;
import com.wimo.exceptions.TransactionLogNotFound;
import com.wimo.model.TransactionLog;

public interface TransactionLogService {
	public String recordTransactionLog(TransactionLog transactionLog);

	public TransactionLog getTransactionLogById(int transactionId) throws TransactionLogNotFound;
	
	public List<TransactionLog> getAllTransactionLogs();

	public String deleteTransactionLog(int transactionId);
	
	public List<TransactionLog> getTransactionLogsByTimestampBetween(LocalDate startDate,LocalDate endDate);

	public List<TransactionLog> getTransactionLogsByPriceBetween(Double initialPrice,Double finalPrice);
	
	public List<TransactionLog> getTransactionLogsByType(String type);

	TransactionStockResponseDTO getTransactionLogsByStock(int stockId);

	TransactionUserResponseDTO getTransactionLogsByUser(int userId);
}

