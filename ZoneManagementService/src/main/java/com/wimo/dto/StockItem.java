package com.wimo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockItem {
	private int stockId;
	private String stockName;
	private String stockCategory;
	private int stockQuantity;
	private int zoneId;
	private int vendorId;

}
