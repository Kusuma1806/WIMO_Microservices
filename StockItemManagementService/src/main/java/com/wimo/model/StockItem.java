package com.wimo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int stockId;

	@NotBlank(message="Stock name cannot be blank")
	private String stockName;

	@NotBlank(message="Stock category cannot be blank")
	private String stockCategory;

	@NotNull(message="Stock quantity cannot be null")
	@Min(value=1, message="Stock quantity must be at least 1")
	private int stockQuantity;

	@Positive(message="Zone Id shouldn't be zero or negative value")
	private int zoneId;

	@NotNull(message="Vendor Id cannot be null")
	private int vendorId;


}
