package com.wimo.service;

import com.wimo.dto.AdminNotificationRequest;
import com.wimo.dto.VendorNotificationRequest;

//Service interface for handling notification-related operations
public interface NotificationService {

	public abstract void sendMail(String toMail, String subject, String body);

	public abstract void sendAdminNotification(AdminNotificationRequest request);

	public abstract void sendVendorNotification(VendorNotificationRequest request);
}