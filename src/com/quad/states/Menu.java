package com.quad.states;


import com.quad.Tile.Background;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.components.State;
import com.quad.core.fx.Image;

public class Menu extends State{
	
	//background
	private Background bg;
	private Background moon;
	
	//images
	private Image titleBlock;


	@Override
	public void init(GameContainer gc) {
		
		//background
		bg = new Background("/backgrounds/trees.gif", 0);
		moon = new Background("/backgrounds/moon.gif", 0);
		
		titleBlock = new Image("/backgrounds/titleBlock.png");
	}

	@Override
	public void update(GameContainer gc, float dt) {
		bg.update();
		bg.setVector(0.8, 0);
		
		moon.update();
		moon.setVector(0.1, 0);
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		//background
		r.setClearColor(0x13355D);
		moon.draw(r);
		bg.draw(r);
		
	}

	@Override
	public void dipose() {
		// TODO Auto-generated method stub
		
	}

}
