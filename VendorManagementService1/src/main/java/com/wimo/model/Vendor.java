package com.wimo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vendor {
	 @Id
	 private int vendorId;			  
	 private String vendorName;
	 private long contactInfo;
	 private int stockId;

}
