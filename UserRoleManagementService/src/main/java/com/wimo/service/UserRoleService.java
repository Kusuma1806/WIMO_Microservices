package com.wimo.service;

import java.util.List;

import com.wimo.exceptions.UserRoleNotFound;
import com.wimo.model.UserRole;

public interface UserRoleService {
	
	public abstract String saveUser(UserRole userRole);

	public abstract UserRole updateUser(UserRole userRole);

	public abstract String removeUser(int userId);

	public abstract UserRole getUserById(int userId) throws UserRoleNotFound;

	public abstract List<UserRole> getAllUsers();

}
