package com.wimo.model;

import jakarta.persistence.Id;

public class StockItem {
	 @Id
	 private int itemId;
	 private String itemName;
	 private String	itemCategory;
	 private int quantity;
	 private int zoneId;

}
