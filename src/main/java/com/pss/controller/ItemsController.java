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

import com.pss.entity.Items;
import com.pss.exception.ResourceNotFoundException;
import com.pss.repository.ItemRepository;
import com.pss.repository.OrderRepository;

@RestController
public class ItemsController {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ItemRepository itemRepository;
	
	

	  @PostMapping("/orders/{ordersId}/items")
	  public ResponseEntity<Items> createItems(@PathVariable(value = "ordersId") Long itemsId
			  ,@RequestBody Items itemsRequest){
	  
		Items findById =  orderRepository.findById(itemsId).map(item -> {
			itemsRequest.setOrder(item);
			  return itemRepository.save(itemsRequest);
		  }).orElseThrow(() -> new ResourceNotFoundException("Not found Items with id = "+ itemsId));
		   return new ResponseEntity<>(findById, HttpStatus.CREATED);
	  }
	  
	  @GetMapping("/items/{id}")
	  public ResponseEntity<Items> getItemsByOrderId(@PathVariable(value = "id") Long id) {
		  Items items = itemRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("Not found Items with id = " + id));

	    return new ResponseEntity<>(items, HttpStatus.OK);
	  }
	  
	  
	  @GetMapping("/orders/{ordersId}/items")
	  public ResponseEntity<List<Items>> getAllITemsByOrderId(@PathVariable(value = "ordersId") Long ordersId) {
	    if (!orderRepository.existsById(ordersId)) {
	      throw new ResourceNotFoundException("Not found orders with id = " + ordersId);
	    }

	    List<Items> comments = itemRepository.findByOrder_Id(ordersId);
	    return new ResponseEntity<>(comments, HttpStatus.OK);
	  }

	  
	  @PutMapping("/items/{id}")
	  public ResponseEntity<Items> updateComment(@PathVariable("id") long id, @RequestBody Items itemRequest) {
		  Items findById = itemRepository.findById(id)
	        .orElseThrow(() -> new ResourceNotFoundException("ItemsId " + id + "not found"));

	    findById.setItemName(itemRequest.getItemName());

	    return new ResponseEntity<>(itemRepository.save(findById), HttpStatus.OK);
	  }
	  

	  @DeleteMapping("/items/{id}")
	  public ResponseEntity<HttpStatus> deleteItems(@PathVariable("id") long id) {
		  itemRepository.deleteById(id);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  }

}
