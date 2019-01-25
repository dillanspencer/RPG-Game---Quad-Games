package com.quad.entity;



import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.components.GameObject;


public class Enemy extends GameObject {
	
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	protected boolean remove;
	protected boolean knockback;
	
	protected boolean flinching;
	protected long flinchCount;
	protected int type;
	
	public Enemy(TileMap tm) {
		super(tm);
		remove = false;
	}
	
	public boolean isFlinching(){return flinching;}
	public boolean isDead() { return dead; }
	public boolean shouldRemove() { return remove; }
	
	public void setType(int i){
		type = i;
	}
	
	public int getType(){return type;}
	public int getDamage() { return damage; }
	public int getHealth(){return health;}
	public int getMaxHealth(){return maxHealth;}
	
	public void hit(int damage) {
		if(dead || flinching) return;
		health -= damage;
		if(health < 0) health = 0;
		if(health == 0) dead = true;
		if(dead) remove = true;
		knockback = true;
		flinching = true;
		flinchCount = 0;
	}
		

	
	public void update(GameContainer gc, float dt) {
		super.updateComponents(gc, dt);
		
	}

	
	public void render(GameContainer gc, Renderer r) {
		super.renderComponents(gc, r);
		
	}

	@Override
	public void componentEvent(String name, GameObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
}














