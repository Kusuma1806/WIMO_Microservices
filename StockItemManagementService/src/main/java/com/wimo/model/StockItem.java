package com.wimo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockItem {
	 @Id
	 private int stockId;
	 private String stockName;
	 private String	stockCategory;
	 private int stockQuantity;
	 private int zoneId;

}
