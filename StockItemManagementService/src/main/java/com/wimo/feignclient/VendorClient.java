package com.wimo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wimo.dto.Vendor;
@FeignClient(name="VENDORMANAGEMENTSERVICE",path="/vendors")
public interface VendorClient {
	@GetMapping("/fetchById/{id}")
	public Vendor viewVendor(@PathVariable("id") int vendorId);

}
