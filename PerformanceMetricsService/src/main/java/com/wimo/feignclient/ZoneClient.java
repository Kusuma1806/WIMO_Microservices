package com.wimo.feignclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wimo.dto.Zone;

@FeignClient(name="ZONEMANAGEMENTSERVICE",path="/zones")
public interface ZoneClient {
	@GetMapping("/fetchById/{id}")
	public Zone viewZone(@PathVariable("id") int zoneId);
	@GetMapping("/fetchAll")
	public List<Zone> viewAll();	
}
