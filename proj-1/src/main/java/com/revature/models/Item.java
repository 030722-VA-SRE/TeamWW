package com.revature.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="items")
public class Item {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable=false)
	private String itemName;
	
	@Column(nullable=false)
	private int value;
	
	private String description;
	
	
	@ManyToOne
	@JoinColumn(nullable = true, name="customer")
	private User userSelected;


	public Item() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Item(int id, String itemName, int value, String description, User userSelected) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.value = value;
		this.description = description;
		this.userSelected = userSelected;
	}



	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public int getValue() {
		return value;
	}


	public void setValue(int value) {
		this.value = value;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public User getUserSelected() {
		return userSelected;
	}


	public void setUserSelected(User userSelected) {
		this.userSelected = userSelected;
	}


	@Override
	public int hashCode() {
		return Objects.hash(description, id, itemName, userSelected, value);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return Objects.equals(description, other.description) && id == other.id
				&& Objects.equals(itemName, other.itemName) && Objects.equals(userSelected, other.userSelected)
				&& value == other.value;
	}


	@Override
	public String toString() {
		return "Item [id=" + id + ", itemName=" + itemName + ", value=" + value + ", description=" + description
				+ ", userSelected=" + userSelected + "]";
	}
	
	
	
	
	
}
