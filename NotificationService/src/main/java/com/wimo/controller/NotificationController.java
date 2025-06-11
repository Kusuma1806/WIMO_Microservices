package com.wimo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wimo.dto.AdminNotificationRequest;
import com.wimo.dto.VendorNotificationRequest;
import com.wimo.repository.NotificationRepository;
import com.wimo.service.NotificationService;

@RestController
@RequestMapping("/notifications")
@CrossOrigin("*")
public class NotificationController {
	@Autowired
	private NotificationRepository repository;

    private final NotificationService notificationService;
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/admin")
    public ResponseEntity<Void> notifyAdmins(@RequestBody AdminNotificationRequest request) {

		logger.info(".......................inside notifyAdmins");
		logger.info(".......................email sending admins:{}", request);
        notificationService.sendAdminNotification(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/vendor")
    public ResponseEntity<Void> notifyVendor(@RequestBody VendorNotificationRequest request) {
    	logger.info(".......................inside notifyVendor");
		logger.info(".......................email sending vendor:{}", request);
        notificationService.sendVendorNotification(request);
        return ResponseEntity.ok().build();
    }
    @GetMapping("/fetchAll")
    public List<AdminNotificationRequest> appNotifyAdmins() {
		logger.info(".......................inside notifyAdmins");
        return repository.findAll();
    }
    @DeleteMapping("/delete/{id}")
    public String DeleteNotifyAdmins(@PathVariable("id") int notificationId) {
		logger.info(".......................inside notifyAdmins");
        repository.deleteById(notificationId);
        return "deleted successfully";
    }
 
}
