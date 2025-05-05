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

import com.wimo.exceptions.ZoneNotFound;
import com.wimo.model.Zone;
import com.wimo.repository.ZoneRepository;
import com.wimo.service.ZoneServiceImpl;

@SpringBootTest
class ZoneManagementServiceApplicationTests {

	@Mock
	ZoneRepository repository;
 
	@InjectMocks
	ZoneServiceImpl service;
 
	@Test
	void saveZoneTest() {
		Zone zone = new Zone(1,"soaps",5,3);
		Mockito.when(repository.save(zone)).thenReturn(zone);
 
		String response = service.saveZone(zone);
		assertEquals("Zone Saved!!!", response);
	}
 
	@Test
	void updateZoneTest() {
		Zone zone = new Zone(1,"soaps",5,3);
		zone.setZoneId(1);
 
		Mockito.when(repository.save(zone)).thenReturn(zone);
 
		Zone updatedZone = service.updateZone(zone);
		assertEquals(zone, updatedZone);
	}
 
	@Test
	void removeZoneTest() {
		int zoneId = 1;
 
		Mockito.doNothing().when(repository).deleteById(zoneId);
 
		String response = service.removeZone(zoneId);
		assertEquals("Zone Deleted!!!", response);
	}
 
	@Test
	void getZoneTest() throws ZoneNotFound {
		int zoneId = 1;
		Zone zone = new Zone(1,"soaps",5,3);
		zone.setZoneId(zoneId);
 
		Mockito.when(repository.findById(zoneId)).thenReturn(Optional.of(zone));
 
		Zone foundZone= service.getZoneById(zoneId);
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
		List<Zone> zones = Arrays.asList(new Zone(1,"soaps",5,3),
				new Zone(1,"soaps",5,3));
 
		Mockito.when(repository.findAll()).thenReturn(zones);
 
		List<Zone> allZones = service.getAllZones();
		assertEquals(zones, allZones);
	}


}
