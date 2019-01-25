package com.quad.test;


import com.quad.core.AbstractGame;
import com.quad.core.GameContainer;
import com.quad.core.Settings;

public class GameManager
{
	
	public static void main(String args[])
	{
		
		GameContainer gc = new GameContainer(new AbstractGame());
		gc.setWidth(Settings.WIDTH);
		gc.setHeight(Settings.HEIGHT);
		gc.setScale(Settings.SCALE);
		gc.setTitle("New Game - Dillan Spencer");
		gc.setClearScreen(true);
		gc.start();
		
	}

	
}
