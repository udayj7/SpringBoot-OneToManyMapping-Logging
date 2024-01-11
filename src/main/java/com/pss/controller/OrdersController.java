package com.pss.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pss.entity.Orders;
import com.pss.exception.ResourceNotFoundException;
import com.pss.repository.OrderRepository;

@RestController
public class OrdersController {
	
	@Autowired
	OrderRepository orderRepository;

	  @PostMapping("/orders")
	  public ResponseEntity<Orders> createOrders(@RequestBody Orders order) {
	    Orders save = orderRepository.save(new Orders(order.getId(),order.getOrderName()));
	    return new ResponseEntity<>(save, HttpStatus.CREATED);
	  }

	
	  @GetMapping("/orders/{id}")
	  public ResponseEntity<Orders> getOrderById(@PathVariable("id") long id) {
		  Orders get = orderRepository.findById(id)
				  .orElseThrow(() -> new ResourceNotFoundException("Not found Orders with id = " + id));
	    return new ResponseEntity<>(get, HttpStatus.OK);
	  }
	
	
	  @GetMapping("/orders")
	  public ResponseEntity<List<Orders>> getAllOrderss() {
	    List<Orders> findAll = orderRepository.findAll();

	    if (findAll.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }

	    return new ResponseEntity<>(findAll, HttpStatus.OK);
	  }
	
	  @PutMapping("/orders/{id}")
	  public ResponseEntity<Orders> updateOrders(@PathVariable("id") long id, @RequestBody Orders o) {
	    Orders orders = orderRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Orders with id = " + id));
	    orders.setOrderName(o.getOrderName());
	    return new ResponseEntity<>(orderRepository.save(orders), HttpStatus.OK);
	  }
	  
	  

	@DeleteMapping("/orders/{id}")
	  public ResponseEntity<HttpStatus> deleteOrders(@PathVariable("id") long id) {
		Orders deleteById = orderRepository.deleteById(id);
		if(deleteById != null)
			  return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.OK);
	  }
}
