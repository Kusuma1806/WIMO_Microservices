package com.wimo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class TransactionLog {
	@Id
	private int transactionId;
	@Positive
	private int stockId;
	@NotNull
	private int userId;
	@Positive
	private int zoneId;
	@Positive
	private int quantity;
	@NotBlank
	private String type;
	@CreationTimestamp
	private LocalDateTime timestamp;
	@Positive
	@NotNull
	private double price;

	public TransactionLog(int userId,int zoneId,int transactionId, int stockId, int quantity, String type,
			double price) {
		super();
		this.transactionId = transactionId;
		this.stockId = stockId;
		this.userId = userId;
		this.quantity = quantity;
		this.type = type;
		this.price = price;
	}

}