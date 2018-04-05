package com.matija.gravitydemo1.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.matija.gravitydemo1.GravityDemo1;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GravityDemo1.WIDTH;
		config.height = GravityDemo1.HEIGHT;
		config.title = GravityDemo1.TITLE;

		new LwjglApplication(new GravityDemo1(), config);
	}
}
