package com.wimo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.wimo.dto.TransactionStockResponseDTO;
import com.wimo.exceptions.StockItemNotFound;
import com.wimo.exceptions.TransactionLogNotFound;
import com.wimo.model.TransactionLog;
import com.wimo.repository.TransactionLogRepository;
import com.wimo.service.TransactionLogServiceImpl;

@SpringBootTest
class TransactionLogManagementApplicationTests {
	@Mock
	TransactionLogRepository repository;

	@InjectMocks
	TransactionLogServiceImpl service;

	@Test
	public void recordTransactionLogTest() throws StockItemNotFound {
		{
			TransactionLog transactionLog = new TransactionLog(2, 1,1, 1, 10, "Inbound", 20000);
			Mockito.when(repository.save(transactionLog)).thenReturn(transactionLog);
			String response = service.recordTransactionLog(transactionLog);
			assertEquals("Transaction Saved!!!", response);
		}
	}

	@Test
	public void getTransactionLogByIdTest() throws TransactionLogNotFound {
		int transactionId = 2;
		TransactionLog transactionLog = new TransactionLog(2, 1, 1,1, 10, "Inbound", 20000);
		Mockito.when(repository.findById(transactionId)).thenReturn(Optional.of(transactionLog));
		TransactionLog response = service.getTransactionLogById(transactionId);
		assertEquals(transactionLog, response);

	}
	
	@Test
	void getTransactionLogNotFoundTest() {
		int transactionId = 1;
 
		Mockito.when(repository.findById(transactionId)).thenReturn(Optional.empty());
 
		assertThrows(TransactionLogNotFound.class, () -> {
			service.getTransactionLogById(transactionId);
		});
	}

	@Test
	public void getAllTransactionLogsTest() {
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1,1, 10, "Inbound", 20000),
				new TransactionLog(3, 1,1, 1,10, "Inbound", 20000));
		Mockito.when(repository.findAll()).thenReturn(transactionLogs);
		List<TransactionLog> response = service.getAllTransactionLogs();
		assertEquals(transactionLogs, response);
	}

	@Test
	public void deleteTransactionLogTest() {
		int transactionId = 10;
		Mockito.doNothing().when(repository).deleteById(transactionId);
		String response = service.deleteTransactionLog(transactionId);
		assertEquals("Transaction Deleted!!!", response);
	}

//	@Test
//	public void getTransactionLogsByTimestampBetween() {
//		String startDate = "2025-05-01";
//		String endDate = "2025-05-02";
//		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 10, "Inbound", 1500),
//				new TransactionLog(3, 1, 1, 10, "Inbound", 20000));
//		Mockito.when(repository.findByTimestampBetween(LocalDate.parse(startDate), LocalDate.parse(endDate)))
//				.thenReturn(transactionLogs);
//		List<TransactionLog> response = service.getTransactionLogsByTimestampBetween(LocalDate.parse(startDate),
//				LocalDate.parse(endDate));
//		assertEquals(transactionLogs, response);
//	}

	@Test
	public void getTransactionLogsByPriceBetweenTest() {
		Double initialPrice = 1000.00;
		Double finalPrice = 25000.00;
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1,1, 10, "Inbound", 1500),
				new TransactionLog(3, 1, 1,1, 10, "Inbound", 20000));
		Mockito.when(repository.findByPriceBetween(initialPrice, finalPrice)).thenReturn(transactionLogs);
		List<TransactionLog> response = service.getTransactionLogsByPriceBetween(initialPrice, finalPrice);
		assertEquals(transactionLogs, response);
	}

	@Test
	public void getTransactionLogsByTypeTest() {
		String type = "Inbound";
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1,1, 1, 10, "Inbound", 1500),
				new TransactionLog(3, 1,1, 1, 10, "Inbound", 20000));
		Mockito.when(repository.findByTypeIs(type)).thenReturn(transactionLogs);
		List<TransactionLog> response = service.getTransactionLogsByType(type);
		assertEquals(transactionLogs, response);

	}

	@Test
	public void getTransactionLogsByStockTest() {
		int stockId = 1;
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1,1, 10, "Inbound", 1500),
				new TransactionLog(3, 1, 1,1, 10, "Inbound", 20000));
		Mockito.when(repository.findByStockIdIs(stockId)).thenReturn(transactionLogs);
		TransactionStockResponseDTO response = service.getTransactionLogsByStock(stockId);
		assertEquals(transactionLogs, response);

	}

}
