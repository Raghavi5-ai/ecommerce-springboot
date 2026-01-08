package com.example.ecomm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecomm.entity.User;

public interface UserRepository extends JpaRepository<User,Long>{

}
