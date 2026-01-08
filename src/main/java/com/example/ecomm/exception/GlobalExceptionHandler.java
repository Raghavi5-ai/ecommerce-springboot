package com.example.ecomm.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger=LoggerFactory.getLogger("GlobalExceptionHandler.class");
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<String> productNotFoundHandler(ProductNotFoundException ex)
	{
		logger.error("Product not found",ex);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> userNotFoundHandler(UserNotFoundException ex)
	{
		logger.error("User not found",ex);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}
	
	@ExceptionHandler(DuplicateRecordException.class)
	public ResponseEntity<String> duplicateExcpetionHandler(DuplicateRecordException ex)
	{
		logger.error("Duplicate record found",ex);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	
	@ExceptionHandler(ProductOutOfStockException.class)
	public ResponseEntity<String> OutOfStockHandler(ProductOutOfStockException ex)
	{
		logger.error("Item pout of stock",ex);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	

}

