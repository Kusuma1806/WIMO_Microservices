package com.wimo.model; // Adjust package based on where you put it

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockUpdateQuantityDto {
    @NotNull(message = "Stock ID cannot be null")
    private Integer stockId; // Use Integer for @NotNull check

    @NotNull(message = "Stock quantity cannot be null")
    @Min(value = 1, message = "Transaction quantity must be at least 1") // Validates the delta quantity
    private Integer stockQuantity; // Use Integer for @NotNull check
}