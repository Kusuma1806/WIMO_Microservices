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
public class Zone {
	
	 @Id
	 private int zoneId;
	 private String zoneName;
     private int totalCapacity;
     private int storedCapacity;
     
     public int getAvailableSpace() {
    	 return totalCapacity-storedCapacity;
     }
}
