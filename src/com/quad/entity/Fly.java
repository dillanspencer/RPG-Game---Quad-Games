package com.quad.entity;

import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.components.GameObject;
import com.quad.core.fx.Image;

public class Fly extends GameObject{

	private int tick;
	private double a;
	private double b;
	
	public Fly(TileMap tm) {
		super(tm);
		
		width = 8;
		height = 7;
		
		tick = 0;
		a = Math.random() * 0.06 + 0.07;
		b = Math.random() * 0.06 + 0.07;
	}

	private Image[] sprites;

	public void init(){
		// load sprites
		try {
			
			Image spritesheet = new Image(
					"/extras/fly.gif"
				);
			
			sprites = new Image[2];
			for(int i = 0; i < sprites.length; i++) {
				sprites[i] = spritesheet.getSubimage(
					i * width,
					0,
					width,
					height
				);
			}
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(10);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void getNextPosition(){
		x += dx;
		y += dy;
	}
	
	
	public void update(GameContainer gc, float dt){
		updateComponents(gc, dt);
		getNextPosition();
		animation.update();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		tick++;
		x = Math.sin(a * tick) + x;
		y = Math.sin(b * tick) + y;
	}
	
	
	public void render(GameContainer gc, Renderer r){
		setMapPosition();
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
