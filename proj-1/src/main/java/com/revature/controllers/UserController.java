package com.revature.controllers;

import java.util.List;
import java.util.UUID;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dto.UserDTO;
import com.revature.exceptions.AuthorizationException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.services.AuthService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService us;
	private AuthService as;
	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	public UserController(UserService us, AuthService as) {
		super();
		this.us = us;
		this.as = as;
	}
	
//	@GetMapping
//	public ResponseEntity<List<User>> getAll() {
//		
//		LOG.info("Testing: Full User Search");
//		return new ResponseEntity<>(us.getAll(), HttpStatus.OK);
//	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> getUsers(@RequestHeader(value = "Authorization", required = false) String token) throws AuthorizationException {
		
		MDC.put("requestId", UUID.randomUUID().toString());
		as.verify(token);
		
		LOG.info("All users HTTP Request");
		return new ResponseEntity<>(us.getUsers(),HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") int id, @RequestHeader("Authorization") String token) {
		
		//if token is null, not if it has correct value
		if (token == null) {
			LOG.warn("Invalid user tried to access /users/id");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		
		MDC.put("userToken", token);
		UserDTO u = us.getUserById(id);
		MDC.clear();
			return new ResponseEntity<>(u, HttpStatus.OK);
	}
	
	
	@PostMapping
	public ResponseEntity<String> createUser(@RequestBody User user, @RequestHeader(value="Authorization", required=true)String token) {
		if (token == null) {
			LOG.warn("Unauthorized user: CREATE user denied");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		MDC.put("requestId", UUID.randomUUID().toString());
		as.verify(token);
		
		User u = us.createUser(user);
		LOG.info("User " + u.getUsername() + " created.");
		return new ResponseEntity<>("User " + u.getUsername() + " was created.", HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") int id, 
										@RequestHeader(value="Authorization", required=true)String token) {
		if (token == null) {
			LOG.warn("Unauthorized user: UPDATE user denied");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		MDC.put("requestId", UUID.randomUUID().toString());
		as.verify(token);
		
		LOG.info("User id " + id + " updated.");		//token added
		return new ResponseEntity<>(us.updateUser(id,user, token), HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") int id, @RequestHeader(value="Authorization", required=true)String token) 
																												throws UserNotFoundException {
		if (token == null) {
			LOG.warn("Unauthorized user: DELETE user denied");
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		}
		MDC.put("requestId", UUID.randomUUID().toString());
		as.verify(token);
		
		us.deleteUser(id);
		LOG.info("User id " + id + " deleted.");
		return new ResponseEntity<>("User deleted", HttpStatus.OK);
	}
	
	
	
	
}
