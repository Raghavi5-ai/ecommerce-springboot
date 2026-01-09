package com.example.ecomm.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.ecomm.entity.User;
import com.example.ecomm.exception.DuplicateRecordException;
import com.example.ecomm.exception.UserNotFoundException;
import com.example.ecomm.repository.UserRepository;

@Service
public class UserService {
	
	private static final Logger logger=LoggerFactory.getLogger(UserService.class);
	@Autowired
	UserRepository userRepo;
	
	public Page<User> getAllUsersPage(Pageable pageable)
	{
		logger.info("Fetching registered users list");
		return userRepo.findAll(pageable);
	}
	
	public User getUserById(long id)
	{
		logger.info("Fetching user deatils for user id "+id);
		User u=userRepo.findById(id).orElseThrow(()->new UserNotFoundException("User not found for id "+id));
		return u;
	}
	
	public User addUsers(User u)
	{
		logger.info("Registering new user");
		try {
			return userRepo.save(u);
		}catch(DataIntegrityViolationException ex)
		{
			throw new DuplicateRecordException("Duplicate email id");
		}
		
	}

	public boolean deleteUserById(long id) {
		logger.info("Deleting user with user id "+id);
		if(userRepo.existsById(id))
		{
			userRepo.deleteById(id);
			return true;
		}	
		return false;
	}

	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

}
