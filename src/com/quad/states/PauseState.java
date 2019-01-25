package com.quad.states;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.Settings;
import com.quad.core.components.State;
import com.quad.core.fx.Image;

public class PauseState extends State{
	
	//Menu
	private Image image;
	private Image keyimg;
	
	//events
	private boolean keys;
	private boolean sound;
	private boolean visual;
	private boolean other;
	
	private int count;
	
	//input
	private boolean blockInput;

	@Override
	public void init(GameContainer gc) {
		Settings.setLight(gc, false);
		image = new Image("/Hud/PauseMenu.gif");
		keyimg = new Image("/Hud/Keys.gif");
		count = 0;
	}

	@Override
	public void update(GameContainer gc, float dt) {

		handleInput(gc);
		if(!keys && !sound && !visual && !other) blockInput = false;
		if(count >= 100) count = 2;
		
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		//menu
		r.drawImage(image);
		
		if(keys) keys(gc, r);
		if(sound) sound(gc, r);
		if(visual) visual(gc, r);
		if(other) other(gc, r);
	}

	@Override
	public void dipose() {
		// TODO Auto-generated method stub
		
	}
	
	///////////////////////////////////////
	
	private void keys(GameContainer gc, Renderer r){
		count++;
		blockInput = true;
		System.out.println(count);
		if(count == 1) r.clear();
		if(count > 1){
			r.drawImage(keyimg);
		}
		
		if(gc.getInput().isKeyPressed(KeyEvent.VK_ESCAPE)){
				keys = false;
				count = 0;
		}
		
	}
	
	private void sound(GameContainer gc, Renderer r){
		System.out.println("in sound");

	}
	
	private void visual(GameContainer gc, Renderer r){
		
	}
	
	private void other(GameContainer gc, Renderer r){
		
	}
	
	
	////////////////////////////////////////
	
	private void handleInput(GameContainer gc){
		if(blockInput)return;
		if(gc.getInput().isKeyPressed(KeyEvent.VK_ESCAPE)){
			gc.getGame().setPaused(gc, false);
			Settings.setLight(gc, true);
		}
		
		
		//mouse
		
		//keys
		if(gc.getInput().getMouseX() >= 75 && gc.getInput().getMouseX() <= 175){
			if(gc.getInput().getMouseY() >= 55 && gc.getInput().getMouseY() <= 85){
				if(gc.getInput().isButtonPressed(1)){
					keys = true;
				}
			}
		}
		
		//sound
		if(gc.getInput().getMouseX() >= 75 && gc.getInput().getMouseX() <= 175){
			if(gc.getInput().getMouseY() >= 110 && gc.getInput().getMouseY() <= 140){
				sound = true;
			}else{
				sound = false;
			}
		}
		
	}

}
