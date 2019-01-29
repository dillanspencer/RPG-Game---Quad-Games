package com.quad.entity.objects;

import java.awt.Rectangle;

import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.components.GameObject;
import com.quad.core.fx.Image;
import com.quad.entity.Animation;
import com.quad.entity.players.GamePlayer;

public class Block extends GameObject{
	
	private Image[] sprites;
	private GamePlayer player;
	
	//collision points
	private Rectangle tl, tr, bl, br;

	public Block(TileMap tm, GamePlayer player2) {
		super(tm);
		player = player2;
		width = 16;
		height = 16;
		cwidth = 16;
		cheight = 16;
		
		fallSpeed = 1;
		maxFallSpeed = 2;
		moveSpeed = 2;
		
		//colisions
		bl = new Rectangle((int)x, (int)y+height, 1, 1);
		br = new Rectangle((int)x + width, (int)y + height, 1, 1);
		tl = new Rectangle((int)x, (int)y, 1, 1);
		tr = new Rectangle((int)x + width, (int)y, 1, 1);
	}
	
	public void init(){
		// load sprites
		try {
			
			Image spritesheet = new Image(
					"/player/block.gif"
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
			animation.setDelay(-1);
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void getNextPosition() {
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
	
	public void update(GameContainer gc, float dt){
		updateComponents(gc, dt);
		animation.update();
		getNextPosition();
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		playerCollision();
		
		//collision
		tl.setLocation((int)x,(int) y);
		tr.setLocation((int)x+width,(int) y);
		bl.setLocation((int)x,(int) y+height);
		br.setLocation((int)x+width,(int) y+height);
	}
	
	
	private void playerCollision(){
		xdest = x + dx;
		ydest = y + dy;
		
		xtemp = x;
		ytemp = y;
		
		//top
		if(player.getdy() > 0) {
			if(player.contains(tl) || player.contains(tr)) {
				player.setDy(0);
				player.setFalling(false);
				//ytemp = (currRow + 2) * tileSize - cheight / 2 - coffy;
			}
			else {
				ytemp += dy;
			}
		}
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
