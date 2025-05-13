package com.wimo.exceptions;

@SuppressWarnings("serial")
public class TransactionLogNotFound extends Exception {
	public TransactionLogNotFound(String message) {
		super(message);
	}
}
