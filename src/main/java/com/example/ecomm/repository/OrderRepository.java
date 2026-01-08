package com.example.ecomm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecomm.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders,Long>{

}
