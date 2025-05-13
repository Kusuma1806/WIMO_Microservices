package com.wimo.exceptions;

@SuppressWarnings("serial")
public class StockItemNotFound extends Exception {
	public StockItemNotFound(String message) {
		super(message);
	}

}
