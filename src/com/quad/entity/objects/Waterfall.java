package com.quad.entity.objects;

import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.components.GameObject;
import com.quad.core.fx.Image;
import com.quad.entity.Animation;

public class Waterfall extends GameObject{

	public Waterfall(TileMap tm) {
		super(tm);
		width = 64;
		height = 165;
	}

	private Image[] sprites;

	public void init(){
		// load sprites
		try {
			
			Image spritesheet = new Image(
					"/extras/waterfall.gif"
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
			
			animation = new Animation();
			animation.setFrames(sprites);
			animation.setDelay(12);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void update(GameContainer gc, float dt){
		updateComponents(gc, dt);
		animation.update();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
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
