package com.wimo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wimo.exceptions.StockItemNotFound;
import com.wimo.exceptions.VendorNotFound;
import com.wimo.exceptions.ZoneNotFound;
import com.wimo.model.Notification;
import com.wimo.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

	@Autowired
	private NotificationService service;

	@PostMapping("/send")
	public Notification sendNotification(@RequestBody Notification notification) throws VendorNotFound {
		Notification sentNotification = service.sendEmail(notification);
		return sentNotification;
	}

	@PostMapping("/low-stock/{stockId}")
	public String notifyLowStock(@PathVariable int stockId) throws StockItemNotFound {
		service.notifyLowStock(stockId);
		return "Low stock notification sent.";
	}

	@PostMapping("/empty-zone/{zoneId}")
	public String notifyEmptyZone(@PathVariable int zoneId) throws ZoneNotFound {
		service.notifyEmptyZone(zoneId);
		return "Empty zone notification sent.";
	}

}
