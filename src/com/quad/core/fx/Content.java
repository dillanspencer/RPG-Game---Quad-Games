package com.quad.core.fx;

public class Content {

	//Bat
	public static Image[][] Inventory = load("/Hud/inventoryScreen.gif", 128, 96);
	public static Image[][] Diamond = load("/extras/diamond.gif", 16, 16);
	public static Image[][] Potion = load("/items/potion.gif", 8, 8);
	public static Image[][] PotionIcon = load("/items/potion.gif", 16, 16);
	public static Image[][] Axe = load("/items/axe.gif", 15, 17);
	public static Image[][] Heart = load("/Hud/heart.gif", 8, 8);
	
	public static Image[][] load(String s, int w, int h) {
		Image[][] ret;
		try {
			Image spritesheet = new Image(s);
			int width = spritesheet.width / w;
			int height = spritesheet.height / h;
			ret = new Image[height][width];
			for(int i = 0; i < height; i++) {
				for(int j = 0; j < width; j++) {
					ret[i][j] = spritesheet.getSubimage(j * w, i * h, w, h);
				}
			}
			return ret;
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error loading graphics.");
			System.exit(0);
		}
		return null;
	}

}
