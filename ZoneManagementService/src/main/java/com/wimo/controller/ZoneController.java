package com.wimo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wimo.model.Zone;
import com.wimo.service.ZoneService;

@RestController
@RequestMapping("/zones")
public class ZoneController {

    @Autowired
    ZoneService service;
    
    @PostMapping("/save")
	public String saveZone(@RequestBody Zone Zone) {
		return service.saveZone(Zone);
	}

	@PutMapping("/update")
	public Zone updateZone(@RequestBody Zone Zone){
		return service.updateZone(Zone);
	}
	@GetMapping("/fetchById/{id}")
	public Zone getZoneById(@PathVariable("id") int ZoneId){
		return service.getZoneById(ZoneId);
	}
	@DeleteMapping("/deleteById/{id}")
	public String removeZone(@PathVariable("id") int ZoneId) {
		return service.removeZone(ZoneId);
	}
	@GetMapping("/fetchAll")
	public List<Zone> getAllZones() {
		return service.getAllZones();
	}

}

