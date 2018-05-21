package com.matija.gravitydemo1;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.matija.gravitydemo1.states.Assets;
import com.matija.gravitydemo1.states.GameStateManager;
import com.matija.gravitydemo1.states.FirstLoadingState;

public class GravityDemo1 extends ApplicationAdapter {

	public static final int WIDTH=480;
	public static final int HEIGHT=800;

	public static final String TITLE = "Gravity";
	private GameStateManager gsm;
	private SpriteBatch batch;
	public AssetManager assets;
	Texture img;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		gsm = new GameStateManager(this);
		assets = new AssetManager();
		//Gdx.gl.glClearColor(1, 0, 0, 1);

		for (String s: Assets.earth){
			System.out.println(s);
		}
		//TODO promjenit nazad u menu state
		gsm.push(new FirstLoadingState(gsm));

	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		/*batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
	}
	
	@Override
	public void dispose () {
		System.out.println("Sve se disposa");
		batch.dispose();
	//	img.dispose();
		//Assets.manager.dispose();
		Assets.dispose();
	}
}
