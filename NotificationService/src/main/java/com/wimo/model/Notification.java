package com.wimo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "emails")
public class Notification {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;
	private String body;
	private int vendorId;
	private String vendorEmail;
	
	public Notification(String body, int vendorId, String vendorEmail) {
		this.body = body;
		this.vendorId = vendorId;
		this.vendorEmail = vendorEmail;
	}


}
