package com.example.ecomm.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecomm.controllers.OrderController;
import com.example.ecomm.entity.Product;
import com.example.ecomm.exception.DuplicateRecordException;
import com.example.ecomm.exception.ProductNotFoundException;
import com.example.ecomm.exception.ProductOutOfStockException;
import com.example.ecomm.repository.ProductRepository;

@Service
public class ProductService {
	private static final Logger logger=LoggerFactory.getLogger(ProductService.class);
	@Autowired 
	ProductRepository prodRepo;
	
	public Product addProducts(Product p)
	{
		logger.info("Stocking products in warehouse");
		try {
			Product prod=prodRepo.save(p);
			return prod;
		}catch(DataIntegrityViolationException e)
		{
			throw new DuplicateRecordException("Product already available in WareHouse. Please update the stock.");
		}

	}

	public Page<Product> getAllProducts(Pageable pageable) {
		logger.info("Fetching existing products list");
		return prodRepo.findAll(pageable);
	}

	public Product getProductById(long id) {
		logger.info("Fetch product deatils for id "+id);
		Product p=prodRepo.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found for id "
				+ id));
		return p;
	}
	
	public void deleteProductById(long id)
	{
		logger.info("Deleting Product for id "+id);
		if(prodRepo.existsById(id))
			prodRepo.deleteById(id);
		else
			throw new ProductNotFoundException("Product not found for id "+id);
	}

	@Transactional
	public void checkAndReduceStock(long productid, int quantity) {		
		logger.info("Checking Product stock for product id "+productid+" for quantity "+quantity);
		Product p=getProductById(productid);
		if(p.getStock()>=quantity)
			p.setStock(p.getStock()-quantity);
		else
			throw new ProductOutOfStockException("Insufficient stock for the requested quantity");
			
	}

	public Product getProductByName(String name) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(prodRepo.findProdByName(name)).orElseThrow(()->new ProductNotFoundException("Product Not Found"));
	}

}
