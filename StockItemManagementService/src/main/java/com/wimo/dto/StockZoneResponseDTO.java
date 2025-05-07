package com.wimo.dto;

import java.util.List;

import com.wimo.model.StockItem;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockZoneResponseDTO {
	private Zone zone;
	private List<StockItem> stock;

}
