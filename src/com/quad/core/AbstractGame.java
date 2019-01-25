package com.quad.core;


import com.quad.core.components.State;
import com.quad.states.LevelOne;
import com.quad.states.Menu;
import com.quad.states.PauseState;
import com.quad.states.TestState;


public class AbstractGame{
	
	private State[] states;
	private int currentState;
	
	//Pause
	private PauseState pauseState;
	private boolean paused;
	
	//states
	public static final int NUMSTATES = 10;
	public static final int MENU = 0;
	public static final int LEVELONE = 1;
	public static final int TEST = 2;
	
	public AbstractGame(){
		
		states = new State[NUMSTATES];
		
		pauseState = new PauseState();
		paused = false;
		
		
		currentState = LEVELONE;
		loadState(currentState);
	}
	
	private void loadState(int state) {
		if(state == MENU) states[currentState] = new Menu();
		if(state == LEVELONE) states[currentState] = new LevelOne();
		if(state == TEST) states[currentState] = new TestState();
	}
	
	private void unloadState(int state) {
		states[state] = null;
	}
	
	public void setState(GameContainer gc,int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
		init(gc);
	}
	
	public void setPaused(GameContainer gc, boolean i){
		paused = i;
		
		if(paused) init(gc);
	}
	
	public void init(GameContainer gc){
		if(paused){
			pauseState.init(gc);
			return;
		}
		if(states[currentState] != null) states[currentState].init(gc);
	}
	
	public void update(GameContainer gc, float dt){
		if(paused){
			pauseState.update(gc, dt);
			return;
		}
		if(states[currentState] != null) states[currentState].update(gc, dt);
	}
	
	public void render(GameContainer gc, Renderer r){
		if(paused){
			pauseState.render(gc, r);
			return;
		}
		if(states[currentState] != null) states[currentState].render(gc, r);
		
	}
	
}
