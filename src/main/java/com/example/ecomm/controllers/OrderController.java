package com.example.ecomm.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecomm.entity.Orders;
import com.example.ecomm.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	private static final Logger logger=LoggerFactory.getLogger(OrderController.class);
	@Autowired
	OrderService orderService;
	@GetMapping()
	public ResponseEntity<List<Orders>> getOrders()
	{
		logger.info("Recieved request to fetch existing orders list");
		List<Orders> o=orderService.getAllOrders();
		if(o==null)
			return ResponseEntity.noContent().build();
		return ResponseEntity.ok(o);
	}
	
	@PostMapping("/createOrder")
	public ResponseEntity<Orders> createOrders(@RequestBody Orders o)
	{
		logger.info("Recieved request to place new order");
		Orders ord=orderService.createOrders(o);
		return ResponseEntity.ok(ord);
	}

}
