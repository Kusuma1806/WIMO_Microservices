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

import com.wimo.dto.StockItem;
import com.wimo.dto.StockZoneResponseDTO;
import com.wimo.exceptions.ZoneNotFound;
import com.wimo.feignclient.StockClient;
import com.wimo.model.Zone;
import com.wimo.repository.ZoneRepository;
import com.wimo.service.ZoneServiceImpl;

@SpringBootTest
class ZoneManagementServiceApplicationTests {

	@Mock
	ZoneRepository repository;

	@InjectMocks
	ZoneServiceImpl service;
	@Mock
	StockClient stockClient;
	@Mock
	StockZoneResponseDTO responseDTO;

	@Test
	void saveZoneTest() {
		Zone zone = new Zone(1, "soaps", 5, 3);
		Mockito.when(repository.save(zone)).thenReturn(zone);

		String response = service.saveZone(zone);
		assertEquals("Zone Saved!!!", response);
	}

	@Test
	void updateZoneTest() {
		Zone zone = new Zone(1, "soaps", 5, 3);
		zone.setZoneId(1);

		Mockito.when(repository.save(zone)).thenReturn(zone);

		Zone updatedZone = service.updateZone(zone);
		assertEquals(zone, updatedZone);
	}

	@Test
	public void testRemoveZone_Success() {
		Zone zone = new Zone(1, "soaps", 5, 3);
		Mockito.when(repository.existsById(zone.getZoneId())).thenReturn(true);
		Mockito.when(stockClient.findByZoneIdIs(zone.getZoneId())).thenReturn(responseDTO);
		for (StockItem stock : responseDTO.getStock()) {
			Mockito.doNothing().when(stockClient).removeStockItem(stock.getStockId());
       }
		Mockito.doNothing().when(repository).deleteById(zone.getZoneId());
		String result = service.removeZone(zone.getZoneId());
		assertEquals("Zone and all the stocks in that are Deleted!!!", result);
	}

	@Test
	public void testRemoveZone_NotFound() {
		Zone zone = new Zone(1, "soaps", 5, 3);
		Mockito.when(repository.existsById(zone.getZoneId())).thenReturn(false);
		String result = service.removeZone(zone.getZoneId());
		assertEquals("Zone Not Found", result);
	}

	@Test
	void getZoneTest() throws ZoneNotFound {
		int zoneId = 1;
		Zone zone = new Zone(1, "soaps", 5, 3);
		zone.setZoneId(zoneId);

		Mockito.when(repository.findById(zoneId)).thenReturn(Optional.of(zone));

		Zone foundZone = service.getZoneById(zoneId);
		assertEquals(zone, foundZone);
	}

	@Test
	void getZoneNotFoundTest() {
		int zoneId = 1;

		Mockito.when(repository.findById(zoneId)).thenReturn(Optional.empty());

		assertThrows(ZoneNotFound.class, () -> {
			service.getZoneById(zoneId);
		});
	}

	@Test
	void getAllZonesTest() {
		List<Zone> zones = Arrays.asList(new Zone(1, "soaps", 5, 3), new Zone(1, "soaps", 5, 3));

		Mockito.when(repository.findAll()).thenReturn(zones);

		List<Zone> allZones = service.getAllZones();
		assertEquals(zones, allZones);
	}

}
