package com.wimo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Zone {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private int zoneId;
	 @NotBlank(message="name should not be blank")
	 @Size(min = 3, max = 20,message="zone name should be in the range of 3-20")
	 private String zoneName;
	 @Max(value=10000001,message="Zone capacity reached the threshold")
     private int totalCapacity;
	 @Max(value=10000000,message="Zone capacity reached the threshold")
     private int storedCapacity;
	 
     public int getAvailableSpace() {
    	 return totalCapacity-storedCapacity;
     }
}
