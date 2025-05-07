package com.wimo.dto;

import java.util.List;

import com.wimo.model.TransactionLog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionStockResponseDTO {
	private StockItem stock;
	private List<TransactionLog> transactionLog;

}
