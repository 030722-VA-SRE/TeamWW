package com.revature.controllers;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.UserDTO;
import com.revature.exceptions.ItemNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Item;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.ItemService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/items")
public class ItemController {
	private static final Logger LOG = LoggerFactory.getLogger(ItemController.class);
	private ItemService is;
	private AuthService as;
	
	@Autowired
	public ItemController(ItemService is) {
		super();
		this.is = is;
		// TODO Auto-generated constructor stub
	}
	

	@GetMapping
	public ResponseEntity<List<Item>> getAll(@RequestParam(name="value", required=false) Integer value, @RequestParam(name="item_name",required=false)String item_name) {
		
		if (value != null) {
			return new ResponseEntity<>(is.getItemByValue(value), HttpStatus.OK);
		}
		if (item_name != null) {
			return new ResponseEntity<>(is.getItemByItemName(item_name),HttpStatus.OK);
		}
		LOG.info("Full item search performed");
		return new ResponseEntity<>(is.getAll(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable("id") int id) {
		
		return new ResponseEntity<>(is.getItemById(id), HttpStatus.OK);
	}

	
	@PutMapping("/{id}")
	public ResponseEntity<Item> updateItem(@RequestBody Item item, @PathVariable("id") int id, 
											@RequestHeader(value="Authorization", required=true)String token) {
		if(token == null) {
			LOG.warn("Unauthorized user: UPDATE item denied");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		MDC.put("requestId", UUID.randomUUID().toString());
//		as.verify(token);
	
		LOG.info("Item id " + id + "updated.");
		return new ResponseEntity<>(is.updateItem(id, item), HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteItem(@PathVariable("id") int id, @RequestHeader(value="Authorization", required=true)String token) throws ItemNotFoundException {
		if (token==null) {
			LOG.warn("Unauthorized user: DELETE item denied");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
//		if (is.getItemById(id) == null) {
//			return new ItemNotFoundException();
//		}
		MDC.put("requestId", UUID.randomUUID().toString());
//		as.verify(token);
		
		is.deleteItem(id);
		LOG.info("Item id " + id + " deleted.");
		return new ResponseEntity<>("Item deleted", HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<String> createItem(@RequestBody Item item, @RequestHeader(value = ("Authorization"), required = true)String token) {
		if (token == null) {
			LOG.warn("Unauthorized user: CREATE item denied");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		MDC.put("requestId", UUID.randomUUID().toString());
//		as.verify(token);
		
		Item i = is.createItem(item);
		LOG.info("Item id: " + i.getId() + ", Item: " + i.getItemName() + " created.");
		return new ResponseEntity<>("Item " + i.getItemName() + " was created.", HttpStatus.CREATED);
	}
	
}
