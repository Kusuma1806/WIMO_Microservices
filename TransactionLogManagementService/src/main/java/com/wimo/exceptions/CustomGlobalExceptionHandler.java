package com.wimo.exceptions;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomGlobalExceptionHandler {
	
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

		Map<String, Object> body = new HashMap<>();
		body.put("timestamp", new Date());
		// Get all errors
		ex.getBindingResult().getAllErrors().forEach(error -> 
				body.put(((FieldError)error).getField(),error.getDefaultMessage()));
		return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(value = TransactionLogNotFound.class)
	public ResponseEntity<ExceptionResponse> handleCustomException(TransactionLogNotFound exception,
			WebRequest webRequest) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setTime(LocalDateTime.now());
		exceptionResponse.setMessage(exception.getMessage());

		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);

	}
	@ExceptionHandler(value = StockItemNotFound.class)
	public ResponseEntity<ExceptionResponse> handleCustomException(StockItemNotFound exception,
			WebRequest webRequest) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setTime(LocalDateTime.now());
		exceptionResponse.setMessage(exception.getMessage());

		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);

	}
	@ExceptionHandler(value = SpaceNotAvailable.class)
	public ResponseEntity<ExceptionResponse> handleCustomException(SpaceNotAvailable exception,
			WebRequest webRequest) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setTime(LocalDateTime.now());
		exceptionResponse.setMessage(exception.getMessage());

		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);

	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ExceptionResponse> handleException(Exception exception, WebRequest webRequest) {

		ExceptionResponse exceptionResponse = new ExceptionResponse();
		exceptionResponse.setStatus(404);
		exceptionResponse.setTime(LocalDateTime.now());
		exceptionResponse.setMessage(exception.getMessage());

		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_ACCEPTABLE);

	}
}