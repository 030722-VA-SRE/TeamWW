package com.revature.services;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.revature.dto.UserDTO;
import com.revature.exceptions.UserAlreadyExistsException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.ItemRepository;
import com.revature.repositories.UserRepository;

@Service
public class UserService {

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class); 
	
	@Autowired
	private UserRepository ur;

	public UserService(UserRepository ur) {
		super(); 
		this.ur = ur;
	}
	

	public UserDTO getUserById(int id) throws UserNotFoundException {
		User user = ur.findById(id).orElseThrow(UserNotFoundException::new);
		LOG.info(MDC.get("userToken"));
		return new UserDTO(user);
	}
	
	
	@Transactional
	public User createUser(User newUser) {
//		if (getUserById(newUser.getId()) != null)
//		figure out user not found exception
		return ur.save(newUser);
		
	}
	
	
//	public List<User> getAll(){
//		return ur.findAll();
//	}
	
	
	
	
	public List<UserDTO> getUsers() {
		List<User> users = ur.findAll();
		
		List<UserDTO> userDto = users.stream()
//				.filter(user -> user.getRole().toString().equals("ADMIN"))    //Returns users of role ADMIN/USER
				.map((user) -> new UserDTO(user))
				.collect(Collectors.toList());
		return userDto;
	}
	
	
	@Transactional
	public User updateUser(int id, User user, @RequestHeader(value = ("Authorization"), required = true)String token) {
		
		
		LOG.info("User id " + ur.getById(id) + " updated.");
		return ur.save(user);
	}
	
	@Transactional
	public void deleteUser(int id) throws UserNotFoundException {	
		getUserById(id);
		
		LOG.info("User id " + ur.getById(id) + " deleted.");
		ur.deleteById(id);
	}
	
	
	
	
	
	
	
}
