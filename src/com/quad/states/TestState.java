package com.quad.states;

import java.awt.event.KeyEvent;

import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.Settings;
import com.quad.core.components.State;
import com.quad.entity.TestPlayer;

public class TestState extends State{
	
	private TestPlayer player;
	private TileMap tm;

	@Override
	public void init(GameContainer gc) {
		// TODO Auto-generated method stub
		tm = new TileMap(gc, 12);
		player = new TestPlayer(tm);
		
		player.setPosition(100, 100);
	}

	@Override
	public void update(GameContainer gc, float dt) {
		// TODO Auto-generated method stub
		player.update(gc, dt);
		player.setRight(gc.getInput().isKey(KeyEvent.VK_RIGHT));
		player.setLeft(gc.getInput().isKey(KeyEvent.VK_LEFT));
		player.setJumping(gc.getInput().isKeyPressed(KeyEvent.VK_UP));
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawFillRect(0, 0, Settings.WIDTH, Settings.HEIGHT, 0x000000);
		player.render(gc, r);
		r.drawFillRect(0, 111, Settings.WIDTH, 100, 100);
	}

	@Override
	public void dipose() {
		// TODO Auto-generated method stub
		
	}

}
