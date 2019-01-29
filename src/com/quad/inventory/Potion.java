package com.quad.inventory;

import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.components.GameObject;
import com.quad.core.fx.Content;
import com.quad.core.fx.Image;
import com.quad.entity.InventoryItem;
import com.quad.entity.Item;
import com.quad.entity.Player;
import com.quad.entity.players.GamePlayer;
import com.quad.entity.players.Gunner;

public class Potion extends Item{
	
	//player
		private GamePlayer player;
		
		//sprites
		private Image[] sprites;

		public Potion(TileMap tm, GamePlayer player2) {
			super(tm);

			player = player2;
						
			moveSpeed = 0.4;
			fallSpeed = 0.15;
			maxFallSpeed = 4.0;
			jumpStart = -5;
			
			width = 8;
			height = 8;
			cwidth = 8;
			cheight = 12;
			
			sprites = Content.Potion[0];
			
			animation.setFrames(sprites);
			animation.setNumFrames(4);
			animation.setDelay(8);
		}
		

		public void update(GameContainer gc, float dt){
			y+=dy;
			dy+= fallSpeed;
			updateComponents(gc, dt);
			checkTileMapCollision();
			calculateCorners(x, ydest + 1);
			
			//check to see if player collides with item, and then adds one health to the player
			if(getRectangle().intersects(player.getRectangle())){
				remove = true;
				player.getInventory().addItem(InventoryItem.potionItem);
			}
		
			animation.update();
			
		}
		
		public void render(Renderer r, GameContainer gc){
			renderComponents(gc, r);
		}

		@Override
		public void componentEvent(String name, GameObject object) {
			
		}

		@Override
		public void dispose() {
			
		}

}
