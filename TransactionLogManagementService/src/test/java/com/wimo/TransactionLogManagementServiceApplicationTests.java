package com.wimo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.wimo.dto.StockItem;
import com.wimo.dto.TransactionStockResponseDTO;
import com.wimo.dto.TransactionUserResponseDTO;
import com.wimo.dto.UserInfo;
import com.wimo.exceptions.SpaceNotAvailable;
import com.wimo.exceptions.StockItemNotFound;
import com.wimo.exceptions.TransactionLogNotFound;
import com.wimo.feignclient.StockClient;
import com.wimo.feignclient.UserClient;
import com.wimo.model.TransactionLog;
import com.wimo.repository.TransactionLogRepository;
import com.wimo.service.TransactionLogServiceImpl;

@SpringBootTest
class TransactionLogManagementApplicationTests {
	@Mock
	TransactionLogRepository repository;

	@InjectMocks
	TransactionLogServiceImpl service;

	@Mock
	private UserClient userClient;
	@Mock

	private StockClient stockClient;

	@Test
	 void testRecordTransactionLog() throws StockItemNotFound, SpaceNotAvailable {

		StockItem stockItem = new StockItem(1, "watch", "gadgets", 20, 3, 1);

		TransactionLog transactionLog = new TransactionLog(1, 1, 1, 1, 1, "inbound", 100000.0);

		Mockito.when(repository.save(transactionLog)).thenReturn(transactionLog);
		Mockito.when(stockClient.viewTransactionByStock(transactionLog.getStockId())).thenReturn(stockItem);
		Mockito.when(stockClient.saveStockItem(stockItem)).thenReturn("Transaction Log Saved!!!");
		String result = service.recordTransactionLog(transactionLog);
		assertEquals("Transaction Saved and Stock Updated!!!", result);
	}

	@Test
	 void testRecordTransactionLog_StockItemNotFound() {
		TransactionLog transactionLog = new TransactionLog(1, 1, 1, 1, 1, "inbound", 100000.0);
		Mockito.when(stockClient.viewTransactionByStock(transactionLog.getStockId())).thenReturn(null);
		assertThrows(StockItemNotFound.class, () -> {
			service.recordTransactionLog(transactionLog);
		});
	}

	@Test
	 void testGetTransactionLogsByStock() {
		StockItem stockItem = new StockItem(1, "watch", "gadgets", 20, 3, 1);
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 1, 10, "Inbound", 20000),
				new TransactionLog(3, 1, 1, 1, 10, "Inbound", 20000));
		Mockito.when(repository.findByStockIdIs(stockItem.getStockId())).thenReturn(transactionLogs);
		Mockito.when(stockClient.viewTransactionByStock(stockItem.getStockId())).thenReturn(stockItem);
		TransactionStockResponseDTO result = service.getTransactionLogsByStock(stockItem.getStockId());
		assertEquals(stockItem, result.getStock());
		assertEquals(transactionLogs, result.getTransactionLog());
	}
	@Test
	 void testGetTransactionLogsByUser() {
		UserInfo user = new UserInfo("Kusuma", "1234", "kusuma@gmail.com", "USER");
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 1, 10, "Inbound", 20000),
				new TransactionLog(3, 1, 1, 1, 10, "Inbound", 20000));
		Mockito.when(repository.findByStockIdIs(user.getId())).thenReturn(transactionLogs);
		Mockito.when(userClient.getUserById(user.getId())).thenReturn(user);
		TransactionUserResponseDTO result = service.getTransactionLogsByUser(user.getId());
		assertEquals(user, result.getUser());
		assertEquals(transactionLogs, result.getTransactionLog());
		}
	@Test
	 void testGetTransactionLogsByTimestampBetween() {
		LocalDateTime startDate = LocalDateTime.now().minusDays(1);
		LocalDateTime endDate = LocalDateTime.now();
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 1, 10, "Inbound", 20000),
				new TransactionLog(3, 1, 1, 1, 10, "Inbound", 20000));
		Mockito.when(repository.findByTimestampBetween(startDate, endDate)).thenReturn(transactionLogs);
		List<TransactionLog> result = service.getTransactionLogsByTimestampBetween(startDate, endDate);
		assertEquals(transactionLogs, result);
		}

	@Test
	 void getTransactionLogByIdTest() throws TransactionLogNotFound {
		int transactionId = 2;
		TransactionLog transactionLog = new TransactionLog(2, 1, 1, 1, 10, "Inbound", 20000);
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
	 void getAllTransactionLogsTest() {
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 1, 10, "Inbound", 20000),
				new TransactionLog(3, 1, 1, 1, 10, "Inbound", 20000));
		Mockito.when(repository.findAll()).thenReturn(transactionLogs);
		List<TransactionLog> response = service.getAllTransactionLogs();
		assertEquals(transactionLogs, response);
	}

	@Test
	 void deleteTransactionLogTest() {
		int transactionId = 10;
		Mockito.doNothing().when(repository).deleteById(transactionId);
		String response = service.deleteTransactionLog(transactionId);
		assertEquals("Transaction Deleted!!!", response);
	}

	@Test
	 void getTransactionLogsByPriceBetweenTest() {
		Double initialPrice = 1000.00;
		Double finalPrice = 25000.00;
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 1, 10, "Inbound", 1500),
				new TransactionLog(3, 1, 1, 1, 10, "Inbound", 20000));
		Mockito.when(repository.findByPriceBetween(initialPrice, finalPrice)).thenReturn(transactionLogs);
		List<TransactionLog> response = service.getTransactionLogsByPriceBetween(initialPrice, finalPrice);
		assertEquals(transactionLogs, response);
	}

	@Test
	 void getTransactionLogsByTypeTest() {
		String type = "Inbound";
		List<TransactionLog> transactionLogs = Arrays.asList(new TransactionLog(2, 1, 1, 1, 10, "Inbound", 1500),
				new TransactionLog(3, 1, 1, 1, 10, "Inbound", 20000));
		Mockito.when(repository.findByTypeIs(type)).thenReturn(transactionLogs);
		List<TransactionLog> response = service.getTransactionLogsByType(type);
		assertEquals(transactionLogs, response);

	}


}
