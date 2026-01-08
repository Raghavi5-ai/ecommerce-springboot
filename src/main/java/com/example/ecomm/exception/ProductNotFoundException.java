package com.example.ecomm.exception;

public class ProductNotFoundException extends RuntimeException{
	public ProductNotFoundException(String msg)
	{
		super(msg);
	}

}
