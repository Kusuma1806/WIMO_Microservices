package com.wimo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wimo.dto.Zone;

@FeignClient(name="ZONEMANAGEMENTSERVICE",path="/zones")
public interface ZoneClient {
	@GetMapping("/fetchById/{id}")
	public Zone viewZone(@PathVariable("id") int zoneId);
	@PutMapping("/update")
	public Zone updateZone(@RequestBody @Validated Zone zone);
}
