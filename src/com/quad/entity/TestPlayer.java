package com.quad.entity;

import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.components.GameObject;

public class TestPlayer extends GameObject{

	public TestPlayer(TileMap tm) {
		super(tm);
		moveSpeed = 0.5f;
		maxSpeed = 1;
		
		moveSpeed = 0.1;
		maxSpeed = 1.6;
		stopSpeed = 0.08;
		fallSpeed = 0.15;
		maxFallSpeed = 10.0;
		jumpStart = -4.2;
		stopJumpSpeed = 0.1;
	}
	
	private void getNextPosition() {
		
		double maxSpeed = this.maxSpeed;
		
		// movement
		if(left) {
			dx -= moveSpeed;
			if(dx < -maxSpeed) {
				dx = -maxSpeed;
			}
		}
		else if(right) {
			dx += moveSpeed;
			if(dx > maxSpeed) {
				dx = maxSpeed;
			}
		}
		else {
			if(dx > 0) {
				dx -= stopSpeed;
				if(dx < 0) {
					dx = 0;
				}
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) {
					dx = 0;
				}
			}
		}
		
		
		// jumping
		if(jumping && !falling) {
			//sfx.get("jump").play();
			dy = jumpStart;
			falling = true;
			//SoundClip.play("jump");
		}
		
		// falling
		if(falling) {
			dy += fallSpeed;
			if(dy < 0 && !jumping) dy += stopJumpSpeed;
			if(dy > maxFallSpeed) dy = maxFallSpeed;
		}
		
	}
	
	public void update(GameContainer gc, float dt) {
		super.updateComponents(gc, dt);
		x += dx;
		y += dy;
		
		if(y > 100) {
			falling = false;
			dy = 0;
		}
		getNextPosition();
	}
	
	public void render(GameContainer gc, Renderer r) {
		r.drawFillRect((int) x, (int) y, 10, 10, 0xffffff);
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
