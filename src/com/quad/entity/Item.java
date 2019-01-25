package com.quad.entity;


import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.components.GameObject;
public class Item extends GameObject{
	
	//event
	protected boolean remove;
	
	//player
	protected Player player;

	public Item(TileMap tm) {
		super(tm);
		remove = false;
	}
	
	public void update(GameContainer gc, float dt){
		
		super.updateComponents(gc, dt);
	}
	
	public void render(Renderer r, GameContainer gc){
		super.renderComponents(gc, r);
		
	}

	@Override
	public void componentEvent(String name, GameObject object) {
		
	}

	@Override
	public void dispose() {
		
	}
	
	public boolean shouldRemove(){
		return remove;
	}

}
