package com.wimo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wimo.model.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
