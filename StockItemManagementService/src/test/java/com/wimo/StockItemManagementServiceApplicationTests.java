package com.wimo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.wimo.dto.StockVendorResponseDTO;
import com.wimo.dto.StockZoneResponseDTO;
import com.wimo.dto.Vendor;
import com.wimo.dto.Zone;
import com.wimo.exceptions.SpaceNotAvailable;
import com.wimo.exceptions.StockItemNotFound;
import com.wimo.exceptions.ZoneNotFound;
import com.wimo.feignclient.VendorClient;
import com.wimo.feignclient.ZoneClient;
import com.wimo.model.StockItem;
import com.wimo.model.StockUpdateQuantityDto;
import com.wimo.repository.StockItemRepository;
import com.wimo.service.StockItemServiceImpl;

@SpringBootTest(classes = StockItemManagementServiceApplication.class)
class StockItemManagementServiceApplicationTests {

	@Mock
	StockItemRepository repository;

	@InjectMocks
	StockItemServiceImpl service;

	@Mock
	private ZoneClient zoneClient;
	@Mock
	private VendorClient vendorClient;
	@Mock
	private StockItem stockItem;
	@Mock
	private StockUpdateQuantityDto stock;
	private Zone zone;
	private Vendor vendor;

	@BeforeEach
	 void setUp() {
		stockItem = new StockItem(1, "watch", "gadgets", 20, 3, 1);
		zone = new Zone(1, "zonename", 1000, 500);
		stock=new StockUpdateQuantityDto(1,100);
		vendor = new Vendor(1, "vendorname","snehithak@gmail.com", 98786675645l);

	}

	@Test
	void saveStockItemTest() throws SpaceNotAvailable, ZoneNotFound {
		when(repository.save(stockItem)).thenReturn(stockItem);
		when(zoneClient.viewZone(stockItem.getZoneId())).thenReturn(zone);
		String response = service.saveStockItem(stockItem);
		assertEquals("StockItem Saved!!!", response);
		assertEquals(520, zone.getStoredCapacity());
	}

	@Test
	void saveStockItemTest_SpaceNotAvailable() {
		zone.setTotalCapacity(510); // Set total capacity less than the updated capacity
		when(zoneClient.viewZone(stockItem.getZoneId())).thenReturn(zone);
		assertThrows(SpaceNotAvailable.class, () -> {
			service.saveStockItem(stockItem);
		});
	}

	@Test
	void updateStockItemForInboundTest() throws ZoneNotFound, SpaceNotAvailable, StockItemNotFound {
		stockItem.setStockId(2);
		when(zoneClient.viewZone(stockItem.getZoneId())).thenReturn(zone);
		when(repository.save(stockItem)).thenReturn(stockItem);
		StockItem updatedStockItem = service.updateStockItemForInbound(stock);

		assertEquals(stockItem, updatedStockItem);
	}

	@Test
	void updateStockItemForOutboundTest() throws ZoneNotFound, StockItemNotFound{
		stockItem.setStockId(2);
		when(zoneClient.viewZone(stockItem.getZoneId())).thenReturn(zone);
		when(repository.save(stockItem)).thenReturn(stockItem);
		StockItem updatedStockItem = service.updateStockItemForOutbound(stock);
		assertEquals(stockItem, updatedStockItem);
	}

	@Test
	void removeStockItemTest() throws StockItemNotFound {
		when(repository.findById(stockItem.getStockId())).thenReturn(Optional.of(stockItem));
		when(zoneClient.viewZone(stockItem.getZoneId())).thenReturn(zone);
		String result = service.removeStockItem(stockItem.getStockId());
		assertEquals("StockItem Deleted and updated the zone capacity!!!", result);
		assertEquals(480, zone.getStoredCapacity());
	}

	@Test
	void getStockItemTest() throws StockItemNotFound {
		int stockId = 1;

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
		List<StockItem> stockItems = Arrays.asList(new StockItem(3, "samsung", "electronics", 20, 3, 1),
				new StockItem(4, "samsung", "electronics", 20, 3, 1));

		Mockito.when(repository.findAll()).thenReturn(stockItems);

		List<StockItem> allStockItems = service.getAllStockItems();
		assertEquals(stockItems, allStockItems);
	}

	@Test
	void getAllStockItemsByCategoryTest() {
		String category = "electronics";
		List<StockItem> stockItems = Arrays.asList(new StockItem(1, "samsung", "electronics", 20, 3, 1),
				new StockItem(1, "samsung", "electronics", 20, 3, 1));

		Mockito.when(repository.findByStockCategoryIs(category)).thenReturn(stockItems);

		List<StockItem> stockItemsByCategory = service.findByStockCategoryIs(category);
		assertEquals(stockItems, stockItemsByCategory);
	}

	@Test
	 void testFindByZoneIdIs() throws ZoneNotFound {
		when(zoneClient.viewZone(1)).thenReturn(zone);
		when(repository.findByZoneIdIs(1)).thenReturn(List.of(stockItem));
		StockZoneResponseDTO result = service.findByZoneIdIs(1);
		assertEquals(zone, result.getZone());
		assertEquals(List.of(stockItem), result.getStock());
	}

	@Test
	 void testFindByVendorIdIs() {
		when(vendorClient.viewVendor(1)).thenReturn(vendor);
		when(repository.findByVendorIdIs(1)).thenReturn(List.of(stockItem));

		StockVendorResponseDTO result = service.findByVendorIdIs(1);
		assertEquals(vendor, result.getVendor());
		assertEquals(List.of(stockItem), result.getStock());
	}

}
