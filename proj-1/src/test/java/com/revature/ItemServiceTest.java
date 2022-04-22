package com.revature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.models.Item;
import com.revature.models.User;
import com.revature.repositories.ItemRepository;
import com.revature.services.ItemService;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

	static ItemRepository iRepo;
	static ItemService iServ;
	static Item item;
	static List<Item> items = new ArrayList<>();
	static User user;
	
	@BeforeAll
	public static void init() {
		iRepo = mock(ItemRepository.class);
		iServ = new ItemService(iRepo);
		item = new Item(1, "test", 20, "info", user);
		items.add(item);
	}
	
	@Test
	public void testGetItems() {
		when(iRepo.findAll()).thenReturn(items);
		assertEquals(items, iServ.getAll());
	}
	
	@Test
	public void testGetItemById() {
		when(iRepo.findById(1)).thenReturn(Optional.of(item));
		assertEquals(item, iServ.getItemById(1));		
	}
	
	@Test
	public void testCreateItem() {
		when(iRepo.save(item)).thenReturn(item);
		assertEquals(item, iServ.createItem(item));
	}
	
	@Test
	public void testDeleteItem() {
		when(iRepo.findById(anyInt())).thenReturn(Optional.of(item));
		doNothing().when(iRepo).delete(any(Item.class));
		iServ.deleteItem(1);
		verify(iRepo, times(1)).delete(item);
	}
	
	@Test
	public void testUpdateItem() {
	when (iRepo.findById(1)).thenReturn(Optional.of(item));
	when (iRepo.save(item)).thenReturn(item);
	assertEquals(item, iServ.updateItem(1, item));
	}
	

	
}
