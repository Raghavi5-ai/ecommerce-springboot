package com.example.ecomm.exception;

public class ProductOutOfStockException extends RuntimeException{
	public ProductOutOfStockException(String msg)
	{
		super(msg);
	}

}
