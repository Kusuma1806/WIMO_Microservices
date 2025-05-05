package com.wimo.model;

import jakarta.persistence.Entity;
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
	 @Positive(message="Id shouldn't be zero or negative value")
	 private int stockId;
	 @NotBlank
	 private String stockName;
	 @NotBlank
	 private String	stockCategory;
	 @NotNull
	 @Min(value=1)
	 private int stockQuantity;
	 @Positive(message="Id shouldn't be zero or negative value")
	 private int zoneId;

}
