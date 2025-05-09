package com.wimo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wimo.dto.StockItem;
import com.wimo.dto.TransactionStockResponseDTO;
import com.wimo.dto.TransactionUserResponseDTO;
import com.wimo.dto.UserRole;
import com.wimo.exceptions.StockItemNotFound;
import com.wimo.exceptions.TransactionLogNotFound;
import com.wimo.feignclient.StockClient;
import com.wimo.feignclient.UserClient;
import com.wimo.model.TransactionLog;
import com.wimo.repository.TransactionLogRepository;

@Service
public class TransactionLogServiceImpl implements TransactionLogService {
    private static final Logger logger = LoggerFactory.getLogger(TransactionLogServiceImpl.class);

    @Autowired
    TransactionLogRepository repository;
    @Autowired
    StockClient stockClient;
    @Autowired
    UserClient userClient;

    @Override
    public String recordTransactionLog(TransactionLog transactionLog) throws StockItemNotFound {
        logger.info("Recording transaction log: {}", transactionLog);
        // Save the transaction log to the repository
        repository.save(transactionLog);
        logger.info("Transaction log saved successfully: {}", transactionLog);
        
        // Update the stock item based on the transaction details
        updateStockItemBasedOnTransaction(transactionLog);
        
        // Return a success message
        return "Transaction Saved and Stock Updated!!!";
    }

    private void updateStockItemBasedOnTransaction(TransactionLog transactionLog) throws StockItemNotFound {
        logger.info("Updating stock item based on transaction: {}", transactionLog);
        // Get the stock ID from the transaction log
        int stockId = transactionLog.getStockId();
        
        // Retrieve the stock item using the stock ID
        StockItem stockItem = stockClient.viewTransactionByStock(stockId);
        
        // Check if the stock item exists
        if (stockItem == null) {
            logger.error("Stock item not found with ID: {}", stockId);
            throw new StockItemNotFound("StockItem Not Found");
        }
        
        // Update the stock item based on the transaction type
        if (transactionLog.getType().equalsIgnoreCase("inbound")) {
            logger.info("Processing inbound transaction for stock item: {}", stockItem);
            // Update the stock item for inbound transaction
            stockClient.updateStockItemForInbound(stockItem);
            
            // Increase the stock quantity based on the transaction quantity
            stockItem.setStockQuantity(stockItem.getStockQuantity() + transactionLog.getQuantity());
            
            // Save the updated stock item to the repository
            stockClient.saveStockItem(stockItem);
            logger.info("Stock item updated successfully for inbound transaction: {}", stockItem);
        } else if (transactionLog.getType().equalsIgnoreCase("outbound")) {
            logger.info("Processing outbound transaction for stock item: {}", stockItem);
            // Update the stock item for outbound transaction
            stockClient.updateStockItemForOutbound(stockItem);
            
            // Decrease the stock quantity based on the transaction quantity
            stockItem.setStockQuantity(stockItem.getStockQuantity() - transactionLog.getQuantity());
            
            // Save the updated stock item to the repository
            stockClient.saveStockItem(stockItem);
            logger.info("Stock item updated successfully for outbound transaction: {}", stockItem);
        } else {
            logger.error("Invalid transaction type: {}", transactionLog.getType());
            // Throw an exception for invalid transaction type
            throw new IllegalArgumentException("Invalid transaction type");
        }
    }

    @Override
    public TransactionLog getTransactionLogById(int transactionId) throws TransactionLogNotFound {
        logger.info("Retrieving transaction log with ID: {}", transactionId);
        // Retrieve the transaction log using the transaction ID
        Optional<TransactionLog> optional = repository.findById(transactionId);
        
        // Check if the transaction log exists
        if (optional.isPresent()) {
            logger.info("Transaction log found: {}", optional.get());
            return optional.get();
        } else {
            logger.error("Transaction log not found with ID: {}", transactionId);
            throw new TransactionLogNotFound("No Transaction found for the given id " + transactionId);
        }
    }

    @Override
    public List<TransactionLog> getAllTransactionLogs() {
        logger.info("Retrieving all transaction logs");
        // Retrieve all transaction logs from the repository
        List<TransactionLog> transactionLogs = repository.findAll();
        logger.info("All transaction logs retrieved successfully");
        return transactionLogs;
    }

    @Override
    public TransactionStockResponseDTO getTransactionLogsByStock(int stockId) {
        logger.info("Retrieving transaction logs for stock ID: {}", stockId);
        // Retrieve transaction logs by stock ID from the repository
        List<TransactionLog> transaction = repository.findByStockIdIs(stockId);
        
        // Retrieve the stock item using the stock ID
        StockItem stock = stockClient.viewTransactionByStock(stockId);
        
        // Create a response DTO with the stock item and transaction logs
        TransactionStockResponseDTO responseDTO = new TransactionStockResponseDTO(stock, transaction);
        logger.info("Transaction logs retrieved successfully for stock ID: {}", stockId);
        return responseDTO;
    }

    @Override
    public TransactionUserResponseDTO getTransactionLogsByUser(int userId) {
        logger.info("Retrieving transaction logs for user ID: {}", userId);
        // Retrieve transaction logs by user ID from the repository
        List<TransactionLog> transaction = repository.findByStockIdIs(userId);
        
        // Retrieve the user details using the user ID
        UserRole user = userClient.viewTransactionByUser(userId);
        
        // Create a response DTO with the user and transaction logs
        TransactionUserResponseDTO responseDTO = new TransactionUserResponseDTO(user, transaction);
        logger.info("Transaction logs retrieved successfully for user ID: {}", userId);
        return responseDTO;
    }

    @Override
    public List<TransactionLog> getTransactionLogsByTimestampBetween(LocalDateTime startDate, LocalDateTime endDate) {
        logger.info("Retrieving transaction logs between timestamps: {} and {}", startDate, endDate);
        // Retrieve transaction logs within the specified timestamp range from the repository
        List<TransactionLog> transactionLogs = repository.findByTimestampBetween(startDate, endDate);
        logger.info("Transaction logs retrieved successfully between timestamps: {} and {}", startDate, endDate);
        return transactionLogs;
    }

    @Override
    public List<TransactionLog> getTransactionLogsByPriceBetween(Double initialPrice, Double finalPrice) {
        logger.info("Retrieving transaction logs between prices: {} and {}", initialPrice, finalPrice);
        // Retrieve transaction logs within the specified price range from the repository
        List<TransactionLog> transactionLogs = repository.findByPriceBetween(initialPrice, finalPrice);
        logger.info("Transaction logs retrieved successfully between prices: {} and {}", initialPrice, finalPrice);
        return transactionLogs;
    }

    @Override
    public List<TransactionLog> getTransactionLogsByType(String type) {
        logger.info("Retrieving transaction logs by type: {}", type);
        // Retrieve transaction logs by type from the repository
        List<TransactionLog> transactionLogs = repository.findByTypeIs(type);
        logger.info("Transaction logs retrieved successfully by type: {}", type);
        return transactionLogs;
    }

    @Override
    public String deleteTransactionLog(int transactionId) {
        logger.info("Deleting transaction log with ID: {}", transactionId);
        // Delete the transaction log using the transaction ID
        repository.deleteById(transactionId);
        logger.info("Transaction log deleted successfully: {}", transactionId);
        
        // Return a success message
        return "Transaction Deleted!!!";
    }
}
