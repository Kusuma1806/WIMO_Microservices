package com.wimo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wimo.dto.AdminNotificationRequest;

public interface NotificationRepository extends JpaRepository<AdminNotificationRequest, Integer>{
	
}
