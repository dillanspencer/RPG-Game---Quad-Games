package com.quad.entity;

import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.fx.Content;
import com.quad.core.fx.Image;

public class InventoryItem{
	
	public static InventoryItem[] items = new InventoryItem[256];
	
	protected Image[] image;
	

	protected String name;
	protected int id;
	
	protected static TileMap tm;
	//image
	protected int imageWidth, imageHeight;
	
	public static final int AXE = 0;
	public static final int POTION = 1;
	
	public static InventoryItem axeItem = new InventoryItem(Content.Axe[0], "Axe", AXE);
	public static InventoryItem potionItem = new InventoryItem(Content.PotionIcon[1], "Potion", POTION);
		
	protected int count;
	
	protected boolean pickedUp = false;
	
	public InventoryItem(Image[] image, String name, int id){
		this.image = image;
		this.name = name;
		this.id = id;
		count = 1;
		items[id] = this;

	}

	
	public void update(GameContainer gc, float dt){
	}
	
	public void render(GameContainer gc, Renderer r){
	}
	
	public InventoryItem createNew(int count){
		InventoryItem i = new InventoryItem(image, name, id);
		i.setPickedUp(true);
		i.setCount(count);
		return i;
	}
	
	public InventoryItem createNew(int x, int y){
		InventoryItem i = new InventoryItem(image, name, id);
		return i;
	}

	//GETTERS SETTERS
	public Image[] getImage() {
		return image;
	}


	public void setImage(Image[] image) {
		this.image = image;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getImageWidth() {
		return imageWidth;
	}


	public void setImageWidth(int imageWidth) {
		this.imageWidth = imageWidth;
	}


	public int getImageHeight() {
		return imageHeight;
	}


	public void setImageHeight(int imageHeight) {
		this.imageHeight = imageHeight;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public boolean isPickedUp() {
		return pickedUp;
	}


	public void setPickedUp(boolean pickedUp) {
		this.pickedUp = pickedUp;
	}

}
