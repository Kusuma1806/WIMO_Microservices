package com.wimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Zone {
	private int zoneId;
	private String zoneName;
	private int userId;
	private int totalCapacity;
	private int storedCapacity;

}
