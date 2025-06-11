package com.wimo.dto;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AdminNotificationRequest {
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Id
	private int notificationId;
    private String stockName;
    private int stockQuantity;
    private String vendorName;
    private String vendorEmail;
    private String zoneName;
    private List<String> adminEmails;
}

