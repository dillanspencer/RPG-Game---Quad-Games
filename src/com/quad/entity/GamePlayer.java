package com.quad.entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.components.GameObject;
import com.quad.core.fx.Image;
import com.quad.inventory.Inventory;

public class GamePlayer extends GameObject{
	
	// player stuff
	protected int lives;
	protected int health;
	protected int maxHealth;
	protected int damage;
	protected boolean knockback;
	protected boolean flinching;
	protected long flinchCount;
	protected int score;
	protected boolean doubleJump;
	protected boolean alreadyDoubleJump;
	protected double doubleJumpStart;
	protected long time;
	protected boolean collision;
	protected Inventory inventory;
	
	//player values
	protected int level;
	protected double exp;
	protected double maxExp;
	
	// actions
	protected boolean dashing;
	protected boolean attacking;
	protected boolean upattacking;
	protected boolean charging;
	protected int chargingTick;
	protected boolean teleporting;
	protected boolean firing;
	
	protected Rectangle ar;
	protected Rectangle aur;
	protected Rectangle cr;
	
	// emotes
	public Image confused;
	public Image surprised;
	public static final int NONE = 0;
	public static final int CONFUSED = 1;
	public static final int SURPRISED = 2;
	private int emote = NONE;
	

	public GamePlayer(TileMap tm) {
		super(tm);
		ar = new Rectangle(0, 0, 0, 0);
		ar.width = 10;
		ar.height = 20;
		aur = new Rectangle((int)x - 30, (int)y - 45, 16, 16);
		cr = new Rectangle(0, 0, 0, 0);
		cr.width = 50;
		cr.height = 40;
		
		width = 16;
		height = 16;
		cwidth = 13;
		cheight = 16;
		coffy = 0;
		coffx = 0;
		collision = true;
		
		moveSpeed = 0.1;
		maxSpeed = 1.6;
		stopSpeed = 0.15;
		fallSpeed = 0.15;
		maxFallSpeed = 10.0;
		jumpStart = -4.2;
		stopJumpSpeed = 0.1;
		doubleJumpStart = -3.5;
		
		damage = 1;
		
		facingRight = true;
		
		lives = 3;
		health = maxHealth = 5;
		
		inventory = new Inventory(this);
	}
	
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }

	public void setEmote(int i) {
		emote = i;
	}
	public void setTeleporting(boolean b) { teleporting = b; }
	
	public void setFalling(boolean b){
		falling = b;
	}
	
	public void setJumping(boolean b) {
		if(knockback) return;
		if(b && !jumping && falling && !alreadyDoubleJump) {
			doubleJump = true;
		}
		jumping = b;
	}
	public void setAttacking() {
		if(knockback) return;
		if(charging) return;
		if(up && !attacking) upattacking = true;
		else attacking = true;
	}
	public void setFiring(){
		if(knockback) return;
		if(charging) return;
		if(!firing)firing = true;
	}
	public void setCharging() {
		if(knockback) return;
		if(!attacking && !upattacking && !charging) {
			charging = true;
			//JukeBox.play("playercharge");
			chargingTick = 0;
		}
	}
	public void setDashing(boolean b) {
		if(!b) dashing = false;
		else if(b && !falling) {
			dashing = true;
		}
	}
	
	public void setDx(double i){
		dx = i;
	}
	
	public void setDy(double i){
		dy = i;
	}
	
	public boolean isDashing() { return dashing; }
	public boolean isFalling(){return falling;}
	
	public void setDead() {
		health = 0;
		stop();
	}
	
	public String getTimeToString() {
		int minutes = (int) (time / 3600);
		int seconds = (int) ((time % 3600) / 60);
		return seconds < 10 ? minutes + ":0" + seconds : minutes + ":" + seconds;
	}
	public long getTime() { return time; }
	public void setTime(long t) { time = t; }
	public void setHealth(int i) { health = i; }
	public void setLives(int i) { lives = i; }
	public void gainLife() { lives++; }
	public void loseLife() { lives--; }
	public int getLives() { return lives; }
	public Inventory getInventory(){return inventory;}
	
	public void increaseScore(int score) {
		this.score += score; 
	}
	
	public int getScore() { return score; }
	
	public void hit(int damage) {
		if(flinching) return;
		//SoundClip.play("hit");
		stop();
		health -= damage;
		if(health < 0) health = 0;
		flinching = true;
		flinchCount = 0;
		if(facingRight) dx = -1;
		else dx = 1;
		dy = -3;
		knockback = true;
		falling = true;
		jumping = false;
	}
	
	public void reset() {
		health = maxHealth;
		facingRight = true;
		currentAction = -1;
		stop();
	}
	
	public void stop() {
		left = right = up = down = flinching = 
			dashing = jumping = attacking = upattacking = charging = firing = false;
	}
	
	public void update(GameContainer gc, float dt) {
		super.updateComponents(gc, dt);
		
	}

	
	public void render(GameContainer gc, Renderer r) {
		super.renderComponents(gc, r);
		
	}
	
	public void postRender(GameContainer gc, Renderer r){
		//inventory
		inventory.render(gc, r);
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
