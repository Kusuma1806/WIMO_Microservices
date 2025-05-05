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

import com.wimo.exceptions.UserRoleNotFound;
import com.wimo.model.UserRole;
import com.wimo.service.UserRoleService;
@RestController
@RequestMapping("/users")
public class UserRoleController {
	
	@Autowired
	UserRoleService service;

	@PostMapping("/save")
	public String saveUser(@RequestBody @Validated UserRole userRole) {
		return service.saveUser(userRole);
	}

	@PutMapping("/update")
	public UserRole updateUser(@RequestBody @Validated UserRole userRole){
		return service.updateUser(userRole);
	}
	@GetMapping("/fetchById/{id}")
	public UserRole getUserById(@PathVariable("id") int userId) throws UserRoleNotFound{
		return service.getUserById(userId);
	}
	@DeleteMapping("/deleteById/{id}")
	public String removeUser(@PathVariable("id") int userId) {
		return service.removeUser(userId);
	}
	@GetMapping("/fetchAll")
	public List<UserRole> getAllUsers() {
		return service.getAllUsers();
	}
}
