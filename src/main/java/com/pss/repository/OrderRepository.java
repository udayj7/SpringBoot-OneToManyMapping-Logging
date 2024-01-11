package com.pss.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pss.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

	Optional<Orders> findById(long id);

	Orders deleteById(long id);



}