package com.example.ecomm.controllers;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.ecomm.entity.User;
import com.example.ecomm.service.UserService;


@RestController
@RequestMapping("/users")
public class UserController {
	private static final Logger logger=LoggerFactory.getLogger(UserController.class);
	@Autowired
	UserService uService;
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<User>> getUsers()
	{
		logger.info("Request Received to fetch all users");
		List<User> users=uService.getAllUsers();
		
		if (users.isEmpty()) {
	        return ResponseEntity.noContent().build(); // 204
	    }
		return ResponseEntity.ok(users); // 200
	}
	
	@GetMapping("/getUsersPage")
	public ResponseEntity<List<User>> getUsersPage(@RequestParam(defaultValue="0") int page,
			@RequestParam(defaultValue="1") int size)
	{
		logger.info("Request Received to fetch all users");
		Pageable pageable=PageRequest.of(page, size, Sort.by("name").descending());
		Page<User> userPage=uService.getAllUsersPage(pageable);
		
		if (userPage.isEmpty()) {
	        return ResponseEntity.noContent().build(); // 204
	    }
		return ResponseEntity.ok(userPage.getContent()); // 200
	}
	
	@GetMapping("/getUserById/{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") long id)
	{
		logger.info("Request Received to fetch details for user id:"+id);
		User u=uService.getUserById(id);
		return ResponseEntity.ok(u); // 200
	}
	
	@PostMapping("/addUsers")
	public ResponseEntity<String> addUsers(@RequestBody User u)
	{
		logger.info("Request Received to register a new user");
		try {
			uService.addUsers(u);
			return ResponseEntity.ok("User successfully added");
		}catch(Exception ex)
		{
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Duplicate email id");
		}
		
	}
	
	@DeleteMapping("/deleteUserById/{id}")
	public ResponseEntity<String> deleteUserById(@PathVariable("id") long id)
	{
		logger.info("Request Received to delete user id "+id);
		boolean delete=uService.deleteUserById(id);
		if(!delete)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
		return ResponseEntity.ok("User deleted successfully");
	}

}
