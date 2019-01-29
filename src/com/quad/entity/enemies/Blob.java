package com.quad.entity.enemies;

import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.Settings;
import com.quad.core.fx.Image;
import com.quad.entity.Enemy;
import com.quad.entity.GamePlayer;
import com.quad.entity.Gunner;
import com.quad.entity.Player;

public class Blob extends Enemy{
	
	private Image[] sprites;
	private GamePlayer player;
	private boolean active;
	
	public Blob(TileMap tm, GamePlayer player2) {
		
		super(tm);
		player = player2;
		
		health = maxHealth = 3;
		
		width = 12;
		height = 12;
		cwidth = 12;
		cheight = 12;
		
		damage = 1;
		moveSpeed = 0.8;
		fallSpeed = 0.15;
		maxFallSpeed = 4.0;
		jumpStart = -5;
		
		try {
		
			Image spritesheet = new Image(
					"/enemies/blob.gif"
				);
			
			sprites = new Image[6];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		animation.setFrames(sprites);
		animation.setDelay(5);
				
		left = true;
		facingRight = false;
		
	}
	
	private void getNextPosition() {
		if(knockback) {
			dy -= fallSpeed * 2;
			if(dy < -1) knockback = false;
			return;
		}
		
		if(left) dx = -moveSpeed;
		else if(right) dx = moveSpeed;
		else dx = 0;
		if(falling) {
			dy += fallSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		if(jumping && !falling) {
			dy = jumpStart;
		}
		
	}
	
	public void update(GameContainer gc, float dt) {
		
		super.updateComponents(gc, dt);
		
		if(!active) {
			if(Math.abs(player.getx() - x) < Settings.WIDTH) active = true;
			return;
		}
		
		// check if done flinching
		if(flinching) {
			flinchCount++;
			if(flinchCount > 20) {
				flinching = false;
			}
		}
		
		getNextPosition();
		checkTileMapCollision();
		calculateCorners(x, ydest + 1);
		if(!bottomLeft) {
			left = false;
			right = facingRight = true;
		}
		if(!bottomRight) {
			left = true;
			right = facingRight = false;
		}
		if(topRight ) {
			left = true;
			right = facingRight = false;
		}
		if(topLeft) {
			left = false;
			right = facingRight = true;
		}
		setPosition(xtemp, ytemp);
		
		if(dx == 0) {
			left = !left;
			right = !right;
			facingRight = !facingRight;
		}
		
		// update animation
		animation.update();
		
	}
	
	public void render(GameContainer gc, Renderer r) {
		
		if(flinching && !knockback) {
			if(flinchCount % 10 < 5) return;
		}
		
		
		super.renderComponents(gc, r);
		
	}

}
