package com.wimo.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.wimo.dto.AdminNotificationRequest;
import com.wimo.dto.VendorNotificationRequest;
@FeignClient(name="NOTIFICATIONSERVICE",path="/notifications")
public interface NotificationClient {

	@PostMapping("/admin")
    public ResponseEntity<Void> notifyAdmins(@RequestBody AdminNotificationRequest request);
	@PostMapping("/vendor")
    public ResponseEntity<Void> notifyVendor(@RequestBody VendorNotificationRequest request);
}
