package com.wimo.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionLog {
	private int transactionId;
	private int stockId;
	private int zoneId;
	private int userId;
	private int quantity;
	private String type;
	private LocalDateTime timestamp;
	private double price;
	public TransactionLog(int transactionId, int stockId, int zoneId, int userId, int quantity, String type,
			double price) {
		super();
		this.transactionId = transactionId;
		this.stockId = stockId;
		this.zoneId = zoneId;
		this.userId = userId;
		this.quantity = quantity;
		this.type = type;
		this.price = price;
	}

}
