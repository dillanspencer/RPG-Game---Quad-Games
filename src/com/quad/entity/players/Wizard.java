package com.quad.entity.players;

import java.util.ArrayList;

import com.quad.Tile.TileMap;
import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.fx.Image;
import com.quad.entity.Enemy;

public class Wizard extends GamePlayer{
	
	// references
		private ArrayList<Enemy> enemies;
		
		//spells
		private boolean teleportUp;
		private boolean teleportDown;
		private boolean teleportLeft;
		private boolean teleportRight;
		private int teleportCount;
		private int teleportDelay;
		
		// animations
		private ArrayList<Image[]> sprites;
		private final int[] NUMFRAMES = {
			5, 6, 1, 1, 3
		};
		private final int[] FRAMEWIDTHS = {
			16, 16, 16, 16, 16
		};
		private final int[] FRAMEHEIGHTS = {
			16, 16, 16, 16, 16
		};
		private final int[] SPRITEDELAYS = {
			10, 5, -1, -1, 5
		};
		
		
		// animation actions
		private static final int IDLE = 0;
		private static final int WALKING = 1;
		private static final int ATTACKING = 4;
		private static final int JUMPING = 2;
		private static final int FALLING = 2;
		private static final int KNOCKBACK = 2;
		private static final int DEAD = 10;
		private static final int TELEPORTING = 10;

		public Wizard(TileMap tm) {
			super(tm);
			
			//teleport
			teleportCount = 0;
			teleportDelay = 100;
			
			// load sprites
			try {
				
				Image spritesheet = new Image(
						"/player/Sprite.gif"
					);
				
				int count = 0;
				sprites = new ArrayList<Image[]>();
				for(int i = 0; i < NUMFRAMES.length; i++) {
					Image[] bi = new Image[NUMFRAMES[i]];
					for(int j = 0; j < NUMFRAMES[i]; j++) {
						bi[j] = spritesheet.getSubimage(
							j * FRAMEWIDTHS[i],
							count,
							FRAMEWIDTHS[i],
							FRAMEHEIGHTS[i]
						);
					}
					sprites.add(bi);
					count += FRAMEHEIGHTS[i];
				}
				
				// emotes
				spritesheet = new Image(
					"/HUD/Emotes.gif"
				);
				confused = spritesheet.getSubimage(
					0, 0, 14, 17
				);
				surprised = spritesheet.getSubimage(
					14, 0, 14, 17
				);
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			
			setAnimation(IDLE);
		}
		
		private void getNextPosition() {
			
			if(knockback) {
				dy += fallSpeed * 2;
				if(!falling) knockback = false;
				return;
			}
			
			double maxSpeed = this.maxSpeed;
			if(dashing) maxSpeed *= 1.75;
			
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
			
			// cannot move while attacking, except in air
			if((attacking || upattacking || charging || firing) &&
				!(jumping || falling)) {
				dx = 0;
			}
			
			// charging
			if(charging) {
				chargingTick++;
				if(facingRight) dx = moveSpeed * (3 - chargingTick * 0.07);
				else dx = -moveSpeed * (3 - chargingTick * 0.07);
			}
			
			// jumping
			if(jumping && !falling) {
				//sfx.get("jump").play();
				dy = jumpStart;
				falling = true;
				//SoundClip.play("jump");
			}
			
			if(doubleJump) {
				dy = doubleJumpStart;
				alreadyDoubleJump = true;
				doubleJump = false;
				//SoundClip.play("jump");
				
			}
			//teleport
			if(teleportUp) {
				y -= 30*1.5;
				falling = false;
				dy = 0;
				dx = 0;
				teleportUp = false;
				teleportCount = 0;
			}
			else if(teleportDown) {
				y += 30*1.5;
				dx = 0;
				teleportDown = false;
				teleportCount = 0;
			}
			else if(teleportLeft) {
				x -= 30*1.5;
				dx = 0;
				teleportLeft = false;
				teleportCount = 0;
			}
			else if(teleportRight) {
				x += 30*1.5;
				dx = 0;
				teleportRight = false;
				teleportCount = 0;
			}
			
			if(!falling) alreadyDoubleJump = false;
			
			// falling
			if(falling) {
				dy += fallSpeed;
				if(dy < 0 && !jumping) dy += stopJumpSpeed;
				if(dy > maxFallSpeed) dy = maxFallSpeed;
			}
			
		}
		
		
		
		public boolean isTeleportUp() {
			return teleportUp;
		}

		public void setTeleportUp(boolean teleportUp) {
			this.teleportUp = teleportUp;
		}

		public boolean isTeleportDown() {
			return teleportDown;
		}

		public void setTeleportDown(boolean teleportDown) {
			this.teleportDown = teleportDown;
		}

		public boolean isTeleportLeft() {
			return teleportLeft;
		}

		public void setTeleportLeft(boolean teleportLeft) {
			this.teleportLeft = teleportLeft;
		}

		public boolean isTeleportRight() {
			return teleportRight;
		}

		public void setTeleportRight(boolean teleportRight) {
			this.teleportRight = teleportRight;
		}

		private void setAnimation(int i) {
			currentAction = i;
			animation.setFrames(sprites.get(currentAction));
			animation.setDelay(SPRITEDELAYS[currentAction]);
			width = FRAMEWIDTHS[currentAction];
			height = FRAMEHEIGHTS[currentAction];
		}
		
		public void init(ArrayList<Enemy> enemies){
			this.enemies = enemies;
		}
		
		public void update(GameContainer gc, float dt) {
			super.update(gc, dt);
			
			time++;
			teleportCount++;
			
			//update bullets
			//check if can fire
			if(teleportCount > teleportDelay) teleportCount = teleportDelay;
			if(teleportCount < teleportDelay ) {
				teleportLeft = teleportRight = 
				teleportUp = teleportDown = false;
			}
			
			inventory.update(gc, dt);
			if(inventory.isActive()) return;
			
			// update position
			boolean isFalling = falling;
			getNextPosition();
			if(collision)checkTileMapCollision();
			setPosition(xtemp, ytemp);
			if(isFalling && !falling) {
				//SoundClip.play("land");
			}
			if(dx == 0) x = (int)x;
			
			// check done flinching
			if(flinching) {
				flinchCount++;
				if(flinchCount > 120) {
					flinching = false;
				}
			}
			//check tile updating ----------------------
			
			//spikes
			if(tileMap.getIndex(rowTile, colTile) == 3 || tileMap.getIndex(rowTile, colTile) == 6 ||
					tileMap.getIndex(rowTile, colTile) == 7 || tileMap.getIndex(rowTile, colTile) == 8){
				dead = true;
				setAnimation(IDLE);
				animation.setDelay(-1);
				animation.setFrame(4);
				left = right = false;
			}
			
			
			//-----------------------------------------
			
			
			// check attack finished
			if(currentAction == ATTACKING) {
				if(animation.hasPlayedOnce()) {
					attacking = false;
				}
			}
			
			// check enemy interaction
			for(int i = 0; i < enemies.size(); i++) {
				
				Enemy e = enemies.get(i);
				
				// check attack
				if(currentAction == ATTACKING &&
						animation.getFrame() == 1 && animation.getCount() == 0) {
					if(e.intersects(ar)) {
						e.hit(damage);
					}
				}
				
				// collision with enemy
				if(!e.isDead() && intersects(e) && !charging && !e.isFlinching()) {
					hit(e.getDamage());
				}
				
				if(e.isDead()) {
					//SoundClip.play("ekill");
				}
				
			}
						
			// set animation, ordered by priority
			if(teleporting) {
				if(currentAction != TELEPORTING) {
					setAnimation(TELEPORTING);
				}
			}
			else if(knockback) {
				if(currentAction != KNOCKBACK) {
					setAnimation(KNOCKBACK);
				}
			}
			else if(health == 0) {
				if(currentAction != DEAD) {
					setAnimation(DEAD);
					animation.setFrame(4);
					animation.setDelay(-1);
				}
			}
			else if(attacking) {
				if(currentAction != ATTACKING) {
					//SoundClip.play("attack");
					setAnimation(ATTACKING);
					ar.y = (int)y - 6;
					if(facingRight) ar.x = (int)x + 16;
					else ar.x = (int)x - 16;
				}
				else {
					if(animation.getFrame() == 4 && animation.getCount() == 0) {
					for(int c = 0; c < 3; c++) {
						if(facingRight);
							/*energyParticles.add(
								new EnergyParticle(
									tileMap, 
									ar.x + ar.width - 4, 
									ar.y + ar.height / 2,
									EnergyParticle.RIGHT));*/
						/*else
							energyParticles.add(
								new EnergyParticle(
									tileMap,
									ar.x + 4,
									ar.y + ar.height / 2,
									EnergyParticle.LEFT));	*/
					}}
				}
			}
			
			else if(dy < 0) {
				if(currentAction != JUMPING) {
					setAnimation(JUMPING);
				}
			}
			else if(dy > 0) {
				if(currentAction != FALLING) {
					if(!dead)
					setAnimation(FALLING);
					
				}
			}
			else if(left || right) {
				if(currentAction != WALKING) {
					setAnimation(WALKING);
				}
			}
			else if(currentAction != IDLE) {
				setAnimation(IDLE);
				animation.setNumFrames(4);
			}
			//check fall damage
			if(dy >= 8.0) fallDamage = true;
			if(bottomLeft || bottomRight){
				if(fallDamage){
					hit(1);
					knockback = true;
					flinching = true;
					fallDamage = false;
				}
			}
			
			animation.update();
			// set direction
			if(!attacking && !upattacking && !charging && !knockback) {
				if(right) facingRight = true;
				if(left) facingRight = false;
			}
		}
		
		public void render(GameContainer gc, Renderer r) {
			// flinch
			if(flinching && !knockback) {
				if(flinchCount % 10 < 5) {
					return;
				}
			}
			
			//teleport
			if(teleportCount >= teleportDelay) {
				if(teleportUp || teleportDown || teleportLeft || teleportRight)return;
			}
			
			super.render(gc, r);
		}

}
