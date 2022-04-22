package com.revature.dto;

import java.util.Objects;

import com.revature.models.Role;
import com.revature.models.User;

public class UserDTO {

	private int id;
	private String username;
	private Role role;
	
	public UserDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UserDTO(User user) {
		super();
		id = user.getId();
		username = user.getUsername();
		role = user.getRole();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", username=" + username + ", role=" + role + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, role, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserDTO other = (UserDTO) obj;
		return id == other.id && role == other.role && Objects.equals(username, other.username);
	}
	
	
	
}
