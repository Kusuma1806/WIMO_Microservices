package com.wimo.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wimo.exceptions.ZoneNotFound;
import com.wimo.model.Zone;
import com.wimo.service.ZoneService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/zones")
@AllArgsConstructor
public class ZoneController {
	
	ZoneService service;
    
    @PostMapping("/save") 
	public String saveZone(@RequestBody @Validated Zone zone) {
		return service.saveZone(zone);
	}

	@PutMapping("/update")
	public Zone updateZone(@RequestBody @Validated Zone zone){
		return service.updateZone(zone);
	}
	@GetMapping("/fetchById/{id}")
	public Zone getZoneById(@PathVariable("id") int zoneId) throws ZoneNotFound{
		return service.getZoneById(zoneId);
	}
	@DeleteMapping("/deleteById/{id}")
	public String removeZone(@PathVariable("id") int zoneId) {
		return service.removeZone(zoneId);
	}
	@GetMapping("/fetchAll")
	public List<Zone> getAllZones() {
		return service.getAllZones();
	}

}

