package com.wimo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wimo.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole,Integer> {

}
