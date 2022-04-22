package com.revature.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No user with given id found")
	@ExceptionHandler(UserNotFoundException.class)
	public void handleUserNotFoundException() {
		LOG.warn("User not found exception handled.");
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No item with given id found")
	@ExceptionHandler(ItemNotFoundException.class)
	public void handleItemNotFoundException() {
		LOG.warn("Item not found exception handled.");
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<String> handleAuthenticationException(AuthenticationException e) {
		LOG.warn("Authentication exception was handled.", e);
		return new ResponseEntity<>("Wrong credentials", HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<String> handleAuthenticationException(AuthorizationException e) {
		LOG.warn("Authorization exception was handled.", e);
		return new ResponseEntity<>("Not Authorized", HttpStatus.UNAUTHORIZED);
	}
	
}
