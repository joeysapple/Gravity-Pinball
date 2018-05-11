package com.matija.gravitydemo1.states;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.matija.gravitydemo1.states.GameStateManager;
import com.matija.gravitydemo1.states.StartState;

public class Menus extends ApplicationAdapter {
    public static final int WIDTH = 480;
    public static final int HEIGHT = 800;

    public static final String TITLE = "Menues";
    private GameStateManager gsm;
    private SpriteBatch batch;

    private Music music;





    @Override
    public void create () {
        batch = new SpriteBatch();
        gsm = new GameStateManager(gsm.game);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        gsm.push(new StartState(gsm));
    }

    @Override
    public void render () {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gsm.update(Gdx.graphics.getDeltaTime());
        gsm.render(batch);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}