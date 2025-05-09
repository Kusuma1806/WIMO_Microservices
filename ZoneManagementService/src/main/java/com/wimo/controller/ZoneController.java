package com.wimo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/zones")
public class ZoneController {

    @Autowired
    ZoneService service;
    
    @PostMapping("/save") //http://localhost:9090/zones/save
	public String saveZone(@RequestBody @Validated Zone zone) {
		return service.saveZone(zone);
	}

	@PutMapping("/update")//http://localhost:9090/zones/update
	public Zone updateZone(@RequestBody @Validated Zone zone){
		return service.updateZone(zone);
	}
	@GetMapping("/fetchById/{id}")//http://localhost:9090/zones/fetchById/{id}
	public Zone getZoneById(@PathVariable("id") int zoneId) throws ZoneNotFound{
		return service.getZoneById(zoneId);
	}
	@DeleteMapping("/deleteById/{id}")//http://localhost:9090/zones/ById/delete{id}
	public String removeZone(@PathVariable("id") int zoneId) {
		return service.removeZone(zoneId);
	}
	@GetMapping("/fetchAll")//http://localhost:9090/zones/fetchAll
	public List<Zone> getAllZones() {
		return service.getAllZones();
	}

}

