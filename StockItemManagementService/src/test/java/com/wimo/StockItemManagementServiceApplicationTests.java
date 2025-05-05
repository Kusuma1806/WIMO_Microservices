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

import com.wimo.exceptions.StockItemNotFound;
import com.wimo.model.StockItem;
import com.wimo.repository.StockItemRepository;
import com.wimo.service.StockItemServiceImpl;

@SpringBootTest
class StockItemManagementServiceApplicationTests {

	 
		@Mock
		StockItemRepository repository;
	 
		@InjectMocks
		StockItemServiceImpl service;
	 
		@Test
		void saveStockItemTest() {
			StockItem stockItem = new StockItem(1,"watch", "gadgets", 20,3);
			Mockito.when(repository.save(stockItem)).thenReturn(stockItem);
	 
			String response = service.saveStockItem(stockItem);
			assertEquals("StockItem Saved!!!", response);
		}
	 
		@Test
		void updateStockItemTest() {
			StockItem stockItem = new StockItem(1,"samsung", "electronics", 20,3);
			stockItem.setStockId(2);
	 
			Mockito.when(repository.save(stockItem)).thenReturn(stockItem);
	 
			StockItem updatedStockItem = service.updateStockItem(stockItem);
			assertEquals(stockItem, updatedStockItem);
		}
	 
		@Test
		void removeStockItemTest() {
			int stockId = 1;
	 
			Mockito.doNothing().when(repository).deleteById(stockId);
	 
			String response = service.removeStockItem(stockId);
			assertEquals("StockItem Deleted!!!", response);
		}
	 
		@Test
		void getStockItemTest() throws StockItemNotFound {
			int stockId = 1;
			StockItem stockItem = new StockItem(1,"samsung", "electronics", 20,3);
	 
			Mockito.when(repository.findById(stockId)).thenReturn(Optional.of(stockItem));
	 
			StockItem foundStockItem = service.getStockItemById(stockId);
			assertEquals(stockItem, foundStockItem);
		}
	 
		@Test
		void getStockItemNotFoundTest() {
			int stockId = 1;
	 
			Mockito.when(repository.findById(stockId)).thenReturn(Optional.empty());
	 
			assertThrows(StockItemNotFound.class, () -> {
				service.getStockItemById(stockId);
			});
		}
	 
		@Test
		void getAllStockItemsTest() {
			List<StockItem> stockItems = Arrays.asList(new StockItem(3,"samsung", "electronics", 20,3),
					new StockItem(4,"samsung", "electronics", 20,3));
	 
			Mockito.when(repository.findAll()).thenReturn(stockItems);
	 
			List<StockItem> allStockItems = service.getAllStockItems();
			assertEquals(stockItems, allStockItems);
		}

	 
		@Test
		void getAllStockItemsByCategoryTest() {
			String category = "electronics";
			List<StockItem> stockItems = Arrays.asList(new StockItem(1,"samsung", "electronics", 20,3),
					new StockItem(1,"samsung", "electronics", 20,3));
	 
			Mockito.when(repository.findByStockCategoryIs(category)).thenReturn(stockItems);
	 
			List<StockItem> stockItemsByCategory = service.findByStockCategoryIs(category);
			assertEquals(stockItems, stockItemsByCategory);
		}
		
		@Test
		void getStockItemsByZoneIdTest() {
			int zoneId = 1;
			StockItem stockItem = new StockItem(1,"samsung", "electronics", 20,3);
	 
			Mockito.when(repository.findById(zoneId)).thenReturn(Optional.of(stockItem));
	 
			List<StockItem> foundStockItem = service.findByZoneIdIs(zoneId);
			assertEquals(stockItem, foundStockItem);
		}
}
