package com.quad.states;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.quad.Tile.TileMap;
import com.quad.core.AbstractGame;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.Settings;
import com.quad.core.components.State;
import com.quad.core.fx.Light;
import com.quad.entity.Enemy;
import com.quad.entity.Fly;
import com.quad.entity.InventoryItem;
import com.quad.entity.Item;
import com.quad.entity.Player;
import com.quad.entity.enemies.Blob;
import com.quad.entity.objects.Block;
import com.quad.entity.objects.Switch;
import com.quad.entity.objects.Teleport;
import com.quad.entity.objects.Waterfall;
import com.quad.entity.players.Gunner;
import com.quad.entity.players.Wizard;
import com.quad.hud.HUD;
import com.quad.inventory.Axe;
import com.quad.inventory.Potion;

public class LevelOne extends State{
	
	//map
	private TileMap tm;
	
	//player
	private Gunner player;
	
	//items
	private ArrayList<Item> items;
	
	//fly
	private ArrayList<Fly> flies;
	
	//waterfall
	private Waterfall fall;
	
	//enemies
	private ArrayList<Enemy> enemies;
	
	//blackout screen
	private ArrayList<Rectangle> tb;
	
	//teleport
	private Teleport port;
	private Teleport endPort;
	
	//switches
	private Switch s1;
	
	//block
	private Block b;
	
	//HUD
	private HUD hud;
	
	//positions
	private int spawnX;
	private int spawnY;
	
	//events
	private int step[];
	private boolean eventFinish;
	private boolean eventComplete;
	private boolean mouseMode;

	@Override
	public void init(GameContainer gc) {
		//light
	
		//Settings.setLight(gc, true);
		
		tm = new TileMap(gc, 16);
		tm.loadTiles("/tiles/future.gif");
		tm.loadMap("/maps/future2.map");
		
		//enemies
		enemies = new ArrayList<Enemy>();
		
		//waterfall
		fall = new Waterfall(tm);
		fall.init();
		fall.setPosition(640, 305);
		
		//spawn
		spawnX = 105;
		spawnY = 320;
		
		//player
		player = new Gunner(tm);
		player.init(enemies);
		player.setPosition(spawnX, spawnY);
		player.getInventory().addItem(InventoryItem.potionItem);
		
		//teleport
		port = new Teleport(tm);
		port.init();
		port.setPosition(30, 64 - 36);
		
		endPort = new Teleport(tm);
		endPort.init();
		endPort.setPosition(870, 380);
		
		//switches
		s1 = new Switch(tm);
		s1.init();
		s1.setPosition(1080, 138);
		
		//items
		items = new ArrayList<Item>();
		
		//HUD
		hud = new HUD(player);
		
		//blackout screen
		tb = new ArrayList<Rectangle>();
		
		flies = new ArrayList<Fly>();
		populateNPC();
		populateEnemies();
		populateItems();
		
		b = new Block(tm, player);
		b.init();
		
		step = new int[5];
		 
	}
	
	private void populateNPC(){
		Fly f;
		
		f = new Fly(tm);
		f.init();
		f.setPosition(130, 300);
		flies.add(f);
		
		f = new Fly(tm);
		f.init();
		f.setPosition(135, 300);
		flies.add(f);
		
		f = new Fly(tm);
		f.init();
		f.setPosition(700, 245);
		flies.add(f);
		
		f = new Fly(tm);
		f.init();
		f.setPosition(90, 16);
		flies.add(f);
		
		f = new Fly(tm);
		f.init();
		f.setPosition(100, 16);
		flies.add(f);
		
		f = new Fly(tm);
		f.init();
		f.setPosition(330, 90);
		flies.add(f);
		
	}
	
	private void populateEnemies(){
		enemies.clear();
		Blob g;
		
		g = new Blob(tm, player);
		g.setPosition(400, 300);
		enemies.add(g);
		
		g = new Blob(tm, player);
		g.setPosition(600, 300);
		enemies.add(g);
		
		g = new Blob(tm, player);
		g.setPosition(630, 300);
		enemies.add(g);
		
	}
	
	private void populateItems(){
		Potion p;
		
		p = new Potion(tm, player);
		p.setPosition(400, 300);
		items.add(p);
		
		Axe a;
		
		a = new Axe(tm, player);
		a.setPosition(990, 90);
		items.add(a);
	}

	@Override
	public void update(GameContainer gc, float dt) {
		//update map
		
		//use mouse mode---------------------------------------------
		if(mouseMode)
		tm.setPosition(
				Settings.WIDTH/2 - ((gc.getInput().getMouseX()) - tm.getx()),
				Settings.HEIGHT/2 - (gc.getInput().getMouseY() - tm.gety())
			);
		else{
			tm.setPosition(
					Settings.WIDTH / 2 - player.getx(),
					Settings.HEIGHT / 2 - player.gety()
					);
		}
		
		if(mouseMode){
			player.setPosition(
					 (gc.getInput().getMouseX() - tm.getx()),
					 (gc.getInput().getMouseY() - tm.gety())
				);
			player.setFalling(false);
		}
		//---------------------------------
		tm.update(dt);
		tm.fixBounds();
		
		//events
		if(eventFinish) eventFinish();
		if(eventComplete) eventComplete(gc);
		
		//player update
		player.update(gc, dt);
		
		if(player.getInventory().isActive()) return;
		
		for(Fly f : flies){
			f.update(gc, dt);
		}
		
		//enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update(gc, dt);
			if(e.shouldRemove()){
				enemies.remove(i);
				i--;
			}
		}
		
		//items
		for(int i = 0; i < items.size(); i++) {
			Item it = items.get(i);
			it.update(gc, dt);
			if(it.shouldRemove()){
				items.remove(i);
				i--;
			}
		}
				
		//teleport
		if(port.contains(player) && gc.getInput().isKeyPressed(KeyEvent.VK_DOWN)){
			player.setMovement(false);
			player.setEmote(Player.SURPRISED);
			spawnX = 930;
			spawnY = 390;
			eventFinish = true;
		}
		
		if(endPort.contains(player) && gc.getInput().isKeyPressed(KeyEvent.VK_DOWN)){
			player.setMovement(false);
			player.setEmote(Player.SURPRISED);
			eventComplete = true;
		}
		
		//check switches
		if(s1.contains(player) && gc.getInput().isKeyPressed(KeyEvent.VK_Z)){
			s1.setHit();
			tm.setTile(7, 66, 5);
			tm.setTile(6, 66, 5);
			tm.setTile(7, 65, 5);
			tm.setTile(6, 65, 5);
			tm.setTile(7, 64, 5);
			tm.setTile(6, 64, 5);
			tm.setTile(7, 63, 5);
			tm.setTile(6, 63, 5);
		}
		//check spikes
		if(player.isDead() || player.getHealth() <= 0) eventFinish = true;
		
		//waterfall
		fall.update(gc, dt);
		
		//teleport
		port.update(gc, dt);
		endPort.update(gc, dt);
		
		//switches
		s1.update(gc, dt);
		
		//input
		input(gc);
	}

	@Override
	public void render(GameContainer gc, Renderer r) {
		r.drawFillRect(0, 0, Settings.WIDTH, Settings.HEIGHT, 0x201429);
		r.setAmbientLight(0x63635d);
		
		//waterfall
		fall.render(gc, r);
		
		//render map
		tm.draw(r);
		
		//teleport
		port.render(gc, r);
		endPort.render(gc, r);
		
		//switches
		s1.render(gc, r);
		
		//render player
		player.render(gc, r);
		r.setAmbientLight(0x011942);
		r.drawLight(new Light(0xadbc34, 300),(int) tm.getx() + player.getx(),(int)tm.gety() +  player.gety());
		
		for(Enemy e : enemies){
			e.render(gc, r);
		}
		
		for(Fly f : flies){
			f.render(gc, r);
		}
		
		for(Item i : items){
			i.render(r, gc);
		}
		player.postRender(gc, r);
		
		//hud
		hud.render(gc, r);
		
		//blackout screen
		for(int i = 0; i < tb.size(); i++){
			Rectangle re = tb.get(i);
			r.drawFillRect((int)re.getX(),(int) re.getY(), (int)re.getWidth(),(int) re.getHeight(), 0x000000);
		}
	}

	@Override
	public void dipose() {
		
	}
	
	private void input(GameContainer gc){
	
		//player
		if(player.isDead() || !player.getMovement()) return;
		player.setRight(gc.getInput().isKey(KeyEvent.VK_RIGHT));
		player.setLeft(gc.getInput().isKey(KeyEvent.VK_LEFT));
		player.setJumping(gc.getInput().isKeyPressed(KeyEvent.VK_UP));
		if(gc.getInput().isKeyPressed(KeyEvent.VK_Z)){
			player.setAttacking();
			//player.setAction(5);
		}
		if(gc.getInput().isKey(KeyEvent.VK_X)) {
			player.setFiring();
		}
//		if(gc.getInput().isKeyPressed(KeyEvent.VK_X) && gc.getInput().isKey(KeyEvent.VK_RIGHT)) {
//			player.setTeleportRight(true);
//		}
//		if(gc.getInput().isKeyPressed(KeyEvent.VK_X) && gc.getInput().isKey(KeyEvent.VK_LEFT)) {
//			player.setTeleportLeft(true);
//		}
//		if(gc.getInput().isKeyPressed(KeyEvent.VK_X) && gc.getInput().isKey(KeyEvent.VK_UP)) {
//			player.setTeleportUp(true);
//		}
//		if(gc.getInput().isKeyPressed(KeyEvent.VK_X) && gc.getInput().isKey(KeyEvent.VK_DOWN)) {
//			player.setTeleportDown(true);
//		}
		
		//exit
		if(gc.getInput().isKeyPressed(KeyEvent.VK_ESCAPE)) System.exit(0);
		
		//mouse mode3
		if(gc.getInput().isKeyPressed(KeyEvent.VK_C)){
			mouseMode = !mouseMode;
		}
	
	}
	
	/////////////////////////////////////////////
	//events
	private void eventFinish(){
		step[0]++;
		
		if(step[0] == 100){
			player.setPosition(spawnX, spawnY);
			player.setMovement(true);
			player.setEmote(Player.NONE);
			player.setDead(false);

			step[0] = 0;
			eventFinish = false;
		}

		
	}
	
	private void eventComplete(GameContainer gc){
		step[1]++;
		
		if(step[1] == 100){
			gc.getGame().setState(gc, AbstractGame.MENU);
		}
	}
	
//	private void reset(){
//		//clear rect
//		tb.clear();
//		
//		//reset player
//		player.setMovement(true);
//		player.setPosition(105, 320);
//		player.setEmote(Player.NONE);
//		player.setDead(false);
//		if(player.getHealth() <= 0) player.setHealth(player.getMaxHealth());
//		
//		//change event
//		eventFinish = false;
//		step[0] = 0;
//		
//	}

}
