package com.hmik.menus.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.hmik.menus.Menus;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Menus.WIDTH;
		config.height = Menus.HEIGHT;
		config.title = Menus.TITLE;
		new LwjglApplication(new Menus(), config);
	}
}
