package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

	static UserRepository uRepo;
	static UserService uServ;
	static List<User> users = new ArrayList<>();
	static User user;
	static Role uRole;
	
	@BeforeAll
	public static void init() {
		
		uRepo = mock(UserRepository.class);
		uServ = new UserService(uRepo);
		user = new User(1, "utest", "ptest", uRole);
		users.add(user);
		
	}
	
	@Test
	public void testGetUsers() {
		when(uRepo.findAll()).thenReturn(users);
		assertEquals(users, uServ.getUsers());
	}
	
	@Test
	public void testGetUserById() {
		when(uRepo.findById(2)).thenReturn(Optional.of(user));
		assertEquals(user, uServ.getUserById(2));
	}
	
	@Test
	public void testCreateUser() {
		when(uRepo.save(user)).thenReturn(user);
		assertEquals(user, uServ.createUser(user));
	}
	
//	@Test
//	public void testDeleteUser() {
//	
//	}
//	
//	@Test
//	public void testUpdateUser() {
//	User userA = new User(2,"u2test", "p2test", Role.ADMIN);
//	User userB = new User(3,"u3test", "p3test", Role.USER);
//	
//	when(uRepo.findById(anyInt())).thenReturn(Optional.of(userB));
//	when(uRepo.save(any(User.class))).thenReturn(userA);
//	assertEquals(userA, uServ.updateUser(userA));	
//	}
}
