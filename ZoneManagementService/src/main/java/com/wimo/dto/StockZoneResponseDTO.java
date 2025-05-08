package com.wimo.dto;

import java.util.List;

import com.wimo.model.Zone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockZoneResponseDTO {
	private List<StockItem> stock;
	private Zone zone;

}
