package com.quad.entity.attack;

import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.components.GameObject;
import com.quad.core.fx.Image;
import com.quad.entity.Animation;

public class Bullet extends GameObject{
	
	private int damage;
	private boolean hit;
	private boolean remove;
	private Image[] sprites;
	private Image[] hitSprites;
	
	public Bullet(TileMap tm, boolean right) {
		super(tm);
		
		facingRight = right;
		
		moveSpeed = 3.8;
		if(right) dx = moveSpeed;
		else dx = -moveSpeed;
		
		width = 10;
		height = 8;
		cwidth = 4;
		cheight = 8;
		damage = 2;
		
		// load sprites
		try {
			
			Image spritesheet = new Image(
					"/player/bullet.gif"
				);
			
			sprites = new Image[4];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
			
			hitSprites = new Image[3];
			for(int i = 0; i < hitSprites.length; i++) {
				hitSprites[i] = spritesheet.getSubimage(
					i * width,
					height,
					width,
					height
				);
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(4);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void setHit() {
		if(hit) return;
		hit = true;
		animation.setFrames(hitSprites);
		animation.setDelay(2);
		dx = 0;
	}
	
	public boolean shouldRemove() { return remove; }
	
	public void update(GameContainer gc, float dt) {
		super.updateComponents(gc, dt);
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit) {
			setHit();
		}
		
		animation.update();
		if(hit && animation.hasPlayedOnce()) {
			remove = true;
		}
		
	}
	
	public void draw(GameContainer gc, Renderer r) {
		
		setMapPosition();
		
		super.renderComponents(gc, r);
		
	}

	@Override
	public void componentEvent(String name, GameObject object) {
		
	}

	@Override
	public void dispose() {
		
	}
	public int getDamage() {
		return damage;
	}
	public void setDamage(int damage) {
		this.damage = damage;
	}

}
