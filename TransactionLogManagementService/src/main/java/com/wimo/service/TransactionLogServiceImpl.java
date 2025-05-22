package com.wimo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wimo.dto.StockItem;
import com.wimo.dto.TransactionStockResponseDTO;
import com.wimo.dto.TransactionUserResponseDTO;
import com.wimo.dto.UserInfo;
import com.wimo.exceptions.StockItemNotFound;
import com.wimo.exceptions.TransactionLogNotFound;
import com.wimo.feignclient.StockClient;
import com.wimo.feignclient.UserClient;
import com.wimo.model.TransactionLog;
import com.wimo.repository.TransactionLogRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionLogServiceImpl implements TransactionLogService {
	private static final Logger logger = LoggerFactory.getLogger(TransactionLogServiceImpl.class);
	@Autowired
	TransactionLogRepository repository;
	@Autowired
	StockClient stockClient;
	@Autowired
	UserClient userClient;

	/**
	 * Records a transaction log and updates the stock item based on the transaction
	 * details.
	 *
	 * @param transactionLog the transaction log to be recorded
	 * @return a success message indicating the transaction was saved and stock
	 *         updated
	 * @throws StockItemNotFound if the stock item is not found
	 */
	@Override
	public String recordTransactionLog(TransactionLog transactionLog) throws StockItemNotFound {
		int stockId = transactionLog.getStockId();
		StockItem stockItem;
		try {
			stockItem = stockClient.viewTransactionByStock(stockId);
		}catch (RuntimeException e) {
			logger.error("Exception caught: {}", e.getMessage());
			throw new StockItemNotFound("StockItem Not Found");
	      }
		logger.info("Recording transaction log: {}", transactionLog);
		repository.save(transactionLog);
		logger.info("Transaction log saved successfully: {}", transactionLog);
		updateStockItemBasedOnTransaction(transactionLog);
		return "Transaction Saved and Stock Updated!!!";
	}



	/**
	 * Updates the stock item based on the transaction details.
	 *
	 * @param transactionLog the transaction log containing the details for updating
	 *                       the stock item
	 * @throws StockItemNotFound if the stock item is not found
	 */
	private void updateStockItemBasedOnTransaction(TransactionLog transactionLog) throws StockItemNotFound {
		logger.info("Updating stock item based on transaction: {}", transactionLog);
		int stockId = transactionLog.getStockId();
		StockItem stockItem = stockClient.viewTransactionByStock(stockId);

		if (stockItem == null) {
			logger.error("Stock item not found with ID: {}", stockId);
			throw new StockItemNotFound("StockItem Not Found");
		}

		if (transactionLog.getType().equalsIgnoreCase("inbound")) {
			logger.info("Processing inbound transaction for stock item: {}", stockItem);
			stockClient.updateStockItemForInbound(stockItem);
			stockItem.setStockQuantity(stockItem.getStockQuantity() + transactionLog.getQuantity());
			stockClient.saveStockItem(stockItem);
			logger.info("Stock item updated successfully for inbound transaction: {}", stockItem);
		} else if (transactionLog.getType().equalsIgnoreCase("outbound")) {
			logger.info("Processing outbound transaction for stock item: {}", stockItem);
			stockClient.updateStockItemForOutbound(stockItem);
			stockItem.setStockQuantity(stockItem.getStockQuantity() - transactionLog.getQuantity());
			stockClient.saveStockItem(stockItem);
			logger.info("Stock item updated successfully for outbound transaction: {}", stockItem);
		} else {
			logger.error("Invalid transaction type: {}", transactionLog.getType());
			throw new IllegalArgumentException("Invalid transaction type");
		}
	}

	/**
	 * Retrieves a transaction log by its ID.
	 *
	 * @param transactionId the ID of the transaction log to be retrieved
	 * @return the transaction log with the specified ID
	 * @throws TransactionLogNotFound if the transaction log is not found
	 */
	@Override
	public TransactionLog getTransactionLogById(int transactionId) throws TransactionLogNotFound {
		logger.info("Retrieving transaction log with ID: {}", transactionId);
		Optional<TransactionLog> optional = repository.findById(transactionId);

		if (optional.isPresent()) {
			logger.info("Transaction log found: {}", optional.get());
			return optional.get();
		} else {
			logger.error("Transaction log not found with ID: {}", transactionId);
			throw new TransactionLogNotFound("No Transaction found for the given id " + transactionId);
		}
	}

	/**
	 * Retrieves all transaction logs.
	 *
	 * @return a list of all transaction logs
	 */
	@Override
	public List<TransactionLog> getAllTransactionLogs() {
		logger.info("Retrieving all transaction logs");
		List<TransactionLog> transactionLogs = repository.findAll();
		logger.info("All transaction logs retrieved successfully");
		return transactionLogs;
	}

	/**
	 * Retrieves transaction logs by stock ID.
	 *
	 * @param stockId the ID of the stock
	 * @return a response DTO containing the stock item and transaction logs
	 */
	@Override
	public TransactionStockResponseDTO getTransactionLogsByStock(int stockId) {
		logger.info("Retrieving transaction logs for stock ID: {}", stockId);
		List<TransactionLog> transaction = repository.findByStockIdIs(stockId);
		StockItem stock = stockClient.viewTransactionByStock(stockId);
		TransactionStockResponseDTO responseDTO = new TransactionStockResponseDTO(stock, transaction);
		logger.info("Transaction logs retrieved successfully for stock ID: {}", stockId);
		return responseDTO;
	}

	/**
	 * Retrieves transaction logs by user ID.
	 *
	 * @param id the ID of the user
	 * @return a response DTO containing the user and transaction logs
	 */
	@Override
	public TransactionUserResponseDTO getTransactionLogsByUser(int id) {
		logger.info("Retrieving transaction logs for user ID: {}", id);
		List<TransactionLog> transaction = repository.findByStockIdIs(id);
		UserInfo user = userClient.getUserById(id);
		TransactionUserResponseDTO responseDTO = new TransactionUserResponseDTO(user, transaction);
		logger.info("Transaction logs retrieved successfully for user ID: {}", id);
		return responseDTO;
	}

	/**
	 * Retrieves transaction logs within a specified timestamp range.
	 *
	 * @param startDate the start timestamp
	 * @param endDate   the end timestamp
	 * @return a list of transaction logs within the specified timestamp range
	 */
	@Override
	public List<TransactionLog> getTransactionLogsByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate) {
		logger.info("Retrieving transaction logs between timestamps: {} and {}", startDate, endDate);
		List<TransactionLog> transactionLogs = repository.findByTimestampBetween(startDate, endDate);
		logger.info("Transaction logs retrieved successfully between timestamps: {} and {}", startDate, endDate);
		return transactionLogs;
	}

	/**
	 * Retrieves transaction logs within a specified price range.
	 *
	 * @param initialPrice the initial price
	 * @param finalPrice   the final price
	 * @return a list of transaction logs within the specified price range
	 */
	@Override
	public List<TransactionLog> getTransactionLogsByPriceBetween(Double initialPrice, Double finalPrice) {
		logger.info("Retrieving transaction logs between prices: {} and {}", initialPrice, finalPrice);
		List<TransactionLog> transactionLogs = repository.findByPriceBetween(initialPrice, finalPrice);
		logger.info("Transaction logs retrieved successfully between prices: {} and {}", initialPrice, finalPrice);
		return transactionLogs;
	}

	/**
	 * Retrieves transaction logs by type.
	 *
	 * @param type the type of transaction
	 * @return a list of transaction logs by type
	 */
	@Override
	public List<TransactionLog> getTransactionLogsByType(String type) {
		logger.info("Retrieving transaction logs by type: {}", type);
		List<TransactionLog> transactionLogs = repository.findByTypeIs(type);
		logger.info("Transaction logs retrieved successfully by type: {}", type);
		return transactionLogs;
	}

	/**
	 * Deletes a transaction log by its ID.
	 *
	 * @param transactionId the ID of the transaction log to be deleted
	 * @return a success message indicating the transaction was deleted
	 */
	@Override
	public String deleteTransactionLog(int transactionId) {
		logger.warn("Deleting transaction log with ID: {}", transactionId);
		repository.deleteById(transactionId);
		logger.info("Transaction log deleted successfully: {}", transactionId);
		return "Transaction Deleted!!!";
	}
}
