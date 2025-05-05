package com.wimo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
	 @Positive(message="Id shouldn't be zero or negative value")
	 private int zoneId;
	 @NotBlank
	 @Size(min = 3, max = 20,message="zone name should be in the range of 3-20")
	 private String zoneName;
	 @Min(value=1,message="Each zone should have atleast 1unit of storage capacity")
	 @Max(value=100,message="Zone capacity reached the threshold")
     private int totalCapacity;
	 @Min(value=1,message="Each zone should have atleast 1unit of storage capacity")
	 @Max(value=100,message="Zone capacity reached the threshold")
     private int storedCapacity;
     
     public int getAvailableSpace() {
    	 return totalCapacity-storedCapacity;
     }
}
