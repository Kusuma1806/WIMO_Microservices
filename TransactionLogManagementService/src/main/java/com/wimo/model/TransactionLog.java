package com.wimo.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int transactionId;

	@Positive(message = "Stock ID must be a positive number.")
	private int stockId;

	@NotNull(message = "User ID cannot be null.")
	private int userId;

	@Positive(message = "Zone ID must be a positive number.")
	private int zoneId;

	@Positive(message = "Quantity must be a positive number.")
	private int quantity;

	@NotBlank(message = "Type cannot be blank.")
	private String type;

	@CreationTimestamp
	private LocalDateTime timestamp;

	@Positive(message = "Price must be a positive number.")
	@NotNull(message = "Price cannot be null.")
	private double price;


	public TransactionLog(int userId,int zoneId,int transactionId, int stockId, int quantity, String type,
			double price) {
		super();
		this.transactionId = transactionId;
		this.stockId = stockId;
		this.userId = userId;
		this.zoneId=zoneId;
		this.quantity = quantity;
		this.type = type;
		this.price = price;
	}

}