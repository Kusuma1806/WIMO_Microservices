package com.wimo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.wimo.dto.UserInfo;


@FeignClient(name="SECURITY-SERVICE",path="/auth")
public interface UserClient {
	@GetMapping("/fetchById/{uid}") 
	public UserInfo getUserById(@PathVariable("uid") int id);

}
