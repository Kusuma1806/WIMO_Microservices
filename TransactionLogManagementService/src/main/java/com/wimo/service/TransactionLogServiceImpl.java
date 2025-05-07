package com.wimo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wimo.dto.StockItem;
import com.wimo.dto.TransactionStockResponseDTO;
import com.wimo.dto.TransactionUserResponseDTO;
import com.wimo.dto.UserRole;
import com.wimo.exceptions.TransactionLogNotFound;
import com.wimo.feignclient.StockClient;
import com.wimo.feignclient.UserClient;
import com.wimo.model.TransactionLog;
import com.wimo.repository.TransactionLogRepository;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {
	@Autowired
	TransactionLogRepository repository;
	@Autowired
	StockClient stockClient;
	@Autowired
	UserClient userClient;

	@Override
	public String recordTransactionLog(TransactionLog transactionLog) {
		repository.save(transactionLog);
		return "Transaction Saved!!!";
	}

	@Override
	public TransactionLog getTransactionLogById(int transactionId) throws TransactionLogNotFound {
		Optional<TransactionLog> optional = repository.findById(transactionId);
		if(optional.isPresent()) {
			return optional.get();
		}
		else
			throw new TransactionLogNotFound("No Transaction found for the given id"+transactionId);
		
	}

	@Override
	public List<TransactionLog> getAllTransactionLogs() {
		return repository.findAll();
	}

	@Override
	public TransactionStockResponseDTO getTransactionLogsByStock(int stockId) {
		List<TransactionLog> transaction=repository.findByStockIdIs(stockId);
		StockItem stock =stockClient.viewTransactionByStock(stockId);
		TransactionStockResponseDTO responseDTO=new TransactionStockResponseDTO(stock,transaction);
		return responseDTO;
	}

	
	@Override
	public TransactionUserResponseDTO getTransactionLogsByUser(int userId) {
		List<TransactionLog> transaction=repository.findByStockIdIs(userId);
		UserRole user=userClient.viewTransactionByUser(userId);
		TransactionUserResponseDTO responseDTO=new TransactionUserResponseDTO(user,transaction);
		return responseDTO;
	}

	@Override
	public List<TransactionLog> getTransactionLogsByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate) {
		return repository.findByTimestampBetween(startDate,endDate);
	}

	@Override
	public List<TransactionLog> getTransactionLogsByPriceBetween(Double initialPrice, Double finalPrice) {
		return repository.findByPriceBetween(initialPrice,finalPrice);
	}

	@Override
	public List<TransactionLog> getTransactionLogsByType(String type) {
		return repository.findByTypeIs(type);
	}

	@Override
	public String deleteTransactionLog(int transactionId) {
		repository.deleteById(transactionId);
		return "Transaction Deleted!!!";
	}

}