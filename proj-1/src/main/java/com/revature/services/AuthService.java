package com.revature.services;

import java.util.Arrays;

import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dto.UserDTO;
import com.revature.exceptions.AuthenticationException;
import com.revature.exceptions.AuthorizationException;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service
public class AuthService {
	private UserRepository ur;
	private static Logger LOG = LoggerFactory.getLogger(AuthService.class);
	
	@Autowired
	public AuthService(UserRepository ur) {
		super();
		this.ur = ur;
	}
 
	
	public String login(String username, String password) {
		User user = ur.findUserByUsername(username);
	
		// basic logic to verify that credentials passed in match db credentials
		if(user == null || !user.getPassword().equals(password)) {
			throw new AuthenticationException("Attempted to login with username: " + username);
		}
		
		LOG.info("User " + user.getUsername() + "'s credentials validated.");
		// return a "token" in the format of [id]:[role], NOT GOOD PRACTICE
		return user.getId()+":"+user.getRole().toString();
	}
	
	
	
	public void verify(String token) {
		// verify that the token passed in is not null
		if(token == null) {
			throw new AuthorizationException("null token");
		}
		
		// basic token is in the format of [id]:[role], split into String[] -> {id, role};
		String[] splitToken = token.split(":");

		// convert the String for the id into an int and query db to retrieve a user, if not found return null
		User principal = ur.findById(Integer.valueOf(splitToken[0])).orElse(null);
		
		// Authentication
		if(principal == null || !principal.getRole().toString().equals(splitToken[1]) || !principal.getRole().toString().equals("ADMIN")) {
			throw new AuthorizationException("Unable to verify token of value: " + splitToken[0] + ", " + splitToken[1]);
		} 
		
		LOG.info("token verified successfully");
		// could log a user id
		MDC.put("userId", principal.getId());
	}


	
	
	

//	public void verify(String token, Role... role) throws AuthorizationException {
//		// TODO Auto-generated method stub
//		if (token == null) {
//			throw new AuthorizationException();
//		}
//		
//		String[] splitToken = token.split(":");
//		
//		User principal = ur.findById(Integer.valueOf(splitToken[0])).orElse(null);
//		
//		//Authenticate
//		if (principal == null || !principal.getRole().toString().equals(splitToken[1]) || !principal.getRole().toString().equals("ADMIN")) {
//			throw new AuthorizationException();
//		}
//		
//		MDC.put("userId", principal.getId());
//	}
//	
	
	
	
//	public String generateToken(UserDTO principal) {
//		//logic to create a token
//		//needs better implementation for a token
//		
//		return principal.getId() + ":" +principal.getUsername();
//	}
	
	
}