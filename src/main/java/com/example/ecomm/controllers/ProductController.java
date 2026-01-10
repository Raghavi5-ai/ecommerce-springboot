package com.example.ecomm.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ecomm.entity.Product;
import com.example.ecomm.service.ProductService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;


@RestController
@RequestMapping("/products")
public class ProductController {
	private static final Logger logger=LoggerFactory.getLogger(ProductController.class);
	@Autowired
	ProductService prodService;
	
	@GetMapping()
	public ResponseEntity<List<Product>> getProducts(@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="10")int size)
	{
		logger.info("Request Received to fetch all products");
		Pageable pagable=PageRequest.of(page, size, Sort.by("name").ascending());
		Page<Product> pageProduct=prodService.getAllProducts(pagable);
		//List<Product> products=prodService.getAllProducts();
		if(pageProduct.isEmpty())
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(pageProduct.getContent());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable long id)
	{
		logger.info("Request Received to fetch details for product id:"+id);
		Product p=prodService.getProductById(id);
		/*if(p==null)
			return ResponseEntity.notFound().build();*/
		return ResponseEntity.ok(p);
	}
	
	@GetMapping("/getProductByName")
	public ResponseEntity<Product> getProductByName(@RequestParam String name)
	{
		Product p=prodService.getProductByName(name);
		if(p==null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(p);
	}
	
	
	@PostMapping("/addProducts")
	public ResponseEntity<Product> addProducts(@Valid@RequestBody  Product p)
	{
		logger.info("Request Received to add a new product stock to warehouse");
		Product prod=prodService.addProducts(p);
		return ResponseEntity.status(HttpStatus.CREATED).body(prod);
	}
	
	@DeleteMapping("/deleteProductById/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable long id)
	{
		logger.info("Request Received to delete product id "+id);
		prodService.deleteProductById(id);
		return ResponseEntity.noContent().build();
	}

}
