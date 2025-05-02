package com.wimo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wimo.model.UserRole;
import com.wimo.repository.UserRoleRepository;

@Service
public class UserRoleServiceImpl implements UserRoleService{

	@Autowired
	UserRoleRepository repository;
	
	@Override
	public String saveUser(UserRole userRole) {
		repository.save(userRole);
		return "User Saved!!!";
	}

	@Override
	public UserRole updateUser(UserRole userRole) {
		return repository.save(userRole);
	}

	@Override
	public String removeUser(int userId) {
		repository.deleteById(userId);
		return "User Deleted!!!";
	}

	@Override
	public UserRole getUserById(int userId) {
		return repository.findById(userId).get();
	}

	@Override
	public List<UserRole> getAllUsers() {
		return repository.findAll();
	}

}
