package com.quad.hud;

import com.quad.core.GameContainer;
import com.quad.core.Renderer;
import com.quad.core.fx.Content;
import com.quad.core.fx.Image;
import com.quad.core.fx.ShadowType;
import com.quad.entity.GamePlayer;
import com.quad.entity.Player;

public class HUD {
	
	//health
	private Image heart = Content.Heart[0][0];
	private GamePlayer p;
	
	public HUD(GamePlayer player){
		this.p = player;
		heart.shadowType = ShadowType.NONE;
	}
	
	
	public void render(GameContainer gc, Renderer r){
		//render lives
		for(int i = 0; i < p.getHealth(); i++){
			r.drawImage(heart, 10 +i * 10, 5);
		}
	}

}
