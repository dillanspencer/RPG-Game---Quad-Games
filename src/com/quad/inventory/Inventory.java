package com.quad.inventory;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.fx.Content;
import com.quad.entity.InventoryItem;
import com.quad.entity.Player;
import com.quad.entity.SaveInfo;
import com.quad.entity.players.GamePlayer;

public class Inventory {
	
	private boolean active = false;
	private ArrayList<InventoryItem> inventoryItems;
	
	private GamePlayer p;
	
	private int invX = 32, invY = 16,
			invWidth = 128, invHeight = 96,
			invListCenterX = invX +24,
			invListCenterY = invY + invHeight / 2 -1,
			invListSpacing = 8;
	
	private int invImageX = 130, invImageY = 25;
	
	private int invCountX = 132, invCountY = 45;
	
	private int selectedItem = 0;
	
	public Inventory(GamePlayer gamePlayer){
		inventoryItems = new ArrayList<InventoryItem>();
		this.p = gamePlayer;
	}
	
	public void update(GameContainer gc, float dt){
		if(gc.getInput().isKeyPressed(KeyEvent.VK_E)) active = !active;
		
		if(!active) return;
		
		//handle input
		if(gc.getInput().isKeyPressed(KeyEvent.VK_UP)) selectedItem--;
		if(gc.getInput().isKeyPressed(KeyEvent.VK_DOWN)) selectedItem++;
		
		if(selectedItem < 0) selectedItem = inventoryItems.size() - 1;
		else if(selectedItem >= inventoryItems.size()) selectedItem = 0;
		
		//handle events
		if(gc.getInput().isKeyPressed(KeyEvent.VK_ENTER)){
			//check current item
			if(inventoryItems.isEmpty()) return;
			if(inventoryItems.get(selectedItem).getId() == InventoryItem.POTION){
				p.setHealth(p.getHealth()+2);
				inventoryItems.get(selectedItem).setCount(inventoryItems.get(selectedItem).getCount()-1);
			}
			
			//check to see if equals 0
			if(inventoryItems.get(selectedItem).getCount() <= 0) inventoryItems.remove(selectedItem);
		}
	}
	
	public void render(GameContainer gc, Renderer r){
		if(!active) return;
		
		r.drawImage(Content.Inventory[0][0], invX, invY, invWidth, invHeight);
		
		int len = inventoryItems.size();
		if(len == 0)
			return;
		
		for(int i = -5;i < 6;i++){
			if(selectedItem + i < 0 || selectedItem + i >= len)
				continue;
			if(i == 0){
				r.drawString( "> " + inventoryItems.get(selectedItem + i).getName() + " <", 0xf6ff00, invListCenterX, invListCenterY + i * invListSpacing);
			}else{
				r.drawString( inventoryItems.get(selectedItem + i).getName(), 0xffffff, invListCenterX + 7, invListCenterY + i * invListSpacing);
			}
		}
		InventoryItem item = inventoryItems.get(selectedItem);
		r.drawImage(item.getImage()[0], invImageX, invImageY);
		r.drawString(Integer.toString(item.getCount()), 0xffffff, invCountX, invCountY);
	}
	
	public void addItem(InventoryItem item){
		for(InventoryItem i : inventoryItems){
			if(i.getId() == item.getId()){
				i.setCount(i.getCount() + item.getCount());
				return;
			}
		}
		inventoryItems.add(item);
	}
	
	public boolean isActive() {
		return active;
	}
	
	public boolean hasItem(String name){
		for(int i = 0; i < inventoryItems.size(); i++){
			if(inventoryItems.get(i).getName() == name) return true;
		}
		return false;
	}
	
	public void removeItem(String name){
		for(int i = 0; i < inventoryItems.size(); i++){
			if(inventoryItems.get(i).getName() == name){
				inventoryItems.remove(i);
				i--;
			}
		}
	}

}
