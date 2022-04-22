package com.revature.services;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.dto.UserDTO;
import com.revature.exceptions.ItemNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Item;
import com.revature.models.User;
import com.revature.repositories.ItemRepository;
import com.revature.repositories.UserRepository;

@Service
public class ItemService {
	private static final Logger LOG = LoggerFactory.getLogger(ItemService.class);
	@Autowired
	
	private ItemRepository ir;
	
	
	public ItemService(ItemRepository ir) {
		super();
		this.ir = ir;
	}
	
	public Item getItemById(int id) throws ItemNotFoundException {
		Item item = ir.findById(id).orElseThrow(ItemNotFoundException::new);
		return item;
	}

	public List<Item> getItemByValue(int value) {
		// TODO Auto-generated method stub
		if (ir.findItemByValue(value).isEmpty()) {
			throw new ItemNotFoundException();
		}
		return ir.findItemByValue(value);
	}

	public List<Item> getItemByItemName(String item_name) {
		// TODO Auto-generated method stub
		if (ir.findItemByItemName(item_name).isEmpty()) {
			throw new ItemNotFoundException();
		}
		return ir.findItemByItemName(item_name);
	}
	
	@Transactional
	public Item createItem(Item newItem) {
		return ir.save(newItem);
	}
	
	public List<Item> getAll(){
		return ir.findAll();
	}
	
	@Transactional
	public Item updateItem(int id, Item item) {
		Item ph = ir.findById(id).orElseThrow(ItemNotFoundException::new);
		item.setId(ph.getId());
		
		return ir.save(item);
	}
	
	@Transactional
	public void deleteItem(int id) throws ItemNotFoundException {
		getItemById(id);	
		ir.deleteById(id);
	}	
	
}
