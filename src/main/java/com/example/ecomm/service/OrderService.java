package com.example.ecomm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecomm.entity.OrderStatus;
import com.example.ecomm.entity.Orders;
import com.example.ecomm.repository.OrderRepository;



@Service
public class OrderService {

	private static final Logger logger=LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private ProductService prodService;
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private UserService userService;
	
	public List<Orders> getAllOrders()
	{
		logger.info("Fetching existing orders list");
		return orderRepo.findAll();
	}
	
	@Transactional
	public Orders createOrders(Orders o)
	{
		logger.info("Placing new order");
		userService.getUserById(o.getUserid());
		prodService.checkAndReduceStock(o.getProductid(),o.getQuantity());
		o.setStatus(OrderStatus.PLACED);
		return orderRepo.save(o);	
	}
}
