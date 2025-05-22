package com.wimo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.wimo.dto.StockItem;
import com.wimo.dto.UserInfo;
import com.wimo.dto.Vendor;
import com.wimo.dto.Zone;
import com.wimo.exceptions.StockItemNotFound;
import com.wimo.exceptions.VendorNotFound;
import com.wimo.exceptions.ZoneNotFound;
import com.wimo.feignclient.StockClient;
import com.wimo.feignclient.UserClient;
import com.wimo.feignclient.VendorClient;
import com.wimo.feignclient.ZoneClient;
import com.wimo.model.Notification;
import com.wimo.repository.NotificationRepository;

@Service
public class NotificationService {

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    VendorClient vendorClient;
    @Autowired
    NotificationRepository repository;
    @Autowired
    StockClient stockClient;
    @Autowired
    UserClient userClient;
    @Autowired
    ZoneClient zoneClient;

    public Notification sendEmail(Notification notification) throws VendorNotFound {
    	int vendorId=notification.getVendorId();
    	Vendor vendor;
    	try {
    		 vendor = vendorClient.getVendorById(vendorId);
    		 if(vendor==null) {
    			 throw new VendorNotFound("Vendor not Found");
    		 }
    	}catch (RuntimeException e) {
            throw new VendorNotFound("This Vendor is not found");
        }
        // Save to DB
        Notification savedNotification = repository.save(notification);

        // Send email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(notification.getVendorEmail());
        message.setSubject("Notification for Vendor ID: " + notification.getVendorId());
        message.setText(notification.getBody());
        mailSender.send(message);
        return savedNotification;
    }

    public void notifyLowStock(int stockId) throws StockItemNotFound {
        StockItem stock;
		try {
			stock = stockClient.getStockById(stockId);
		}catch (RuntimeException e) {
			throw new StockItemNotFound("StockItem Not Found");
	      }
        Vendor vendor=vendorClient.getVendorById(stock.getVendorId());

        String subject = "Low Stock Alert: " + stock.getStockName();
        String body = "Stock item '" + stock.getStockName() + "' is low (Quantity: " + stock.getStockQuantity() + ").";
        sendEmail(vendor.getVendorEmail(), subject, body);
    }

    public void notifyEmptyZone(int zoneId) throws ZoneNotFound {
        Zone zone;
        try {
            zone = zoneClient.viewZone(zoneId);
        } catch (RuntimeException e) {
            throw new ZoneNotFound("Zone ID not found");
        }
        UserInfo admin=userClient.getUserById(zone.getUserId());
        String subject = "Zone Empty Alert: " + zone.getZoneName();
        String body = "Zone '" + zone.getZoneName() + "' (ID: " + zone.getZoneId() + ") is now empty.";
        sendEmail(admin.getEmail(), subject, body);
    }

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

}


