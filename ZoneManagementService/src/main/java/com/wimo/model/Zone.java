package com.wimo.model;

import jakarta.persistence.Entity;
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
	 @Positive(message="Id shouldn't be zero or negative value")
	 private int zoneId;
	 @NotBlank(message="name shpuld not be blank")
	 @Size(min = 3, max = 20,message="zone name should be in the range of 3-20")
	 private String zoneName;
	 private int userId;
	 @Max(value=100001,message="Zone capacity reached the threshold")
     private int totalCapacity;
	 @Max(value=100000,message="Zone capacity reached the threshold")
     private int storedCapacity;
	 
     public int getAvailableSpace() {
    	 return totalCapacity-storedCapacity;
     }
}
