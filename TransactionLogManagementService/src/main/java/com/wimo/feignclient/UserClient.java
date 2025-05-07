package com.wimo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wimo.dto.UserRole;


@FeignClient(name="USERROLEMANAGEMENTSERVICE",path="/users")
public interface UserClient {
	@GetMapping("/fetchById/{id}")
	public UserRole viewTransactionByUser(@PathVariable("id") int userId); 

}
