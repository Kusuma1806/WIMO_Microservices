package com.wimo.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminNotificationRequest {
	private String stockName;
    private int stockQuantity;
    private String vendorName;
    private String vendorEmail;
    private String zoneName;
    private List<String> adminEmails;
}
