package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.matija.gravitydemo1.GravityDemo1;
import com.matija.gravitydemo1.utilities.ShapeFactory;

/**
 * Created by Korisnik on 23.3.2018..
 */

/**
 * Klasa koja predstavlja stanje izbornika. Nasljeđuje klasu State.
 */

public class MenuState extends State {

    private Texture backround;
    private Texture playBtn;

    /**
     * Konstruktor
     * @param gsm Upravljač stanjima
     */
    public MenuState(GameStateManager gsm){
        super(gsm);
        backround = new Texture("badlogic.jpg");
        playBtn = new Texture("playBtn.png");
        cam.setToOrtho(false, GravityDemo1.WIDTH/2,GravityDemo1.HEIGHT/2);
    }
    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        this.handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(playBtn,50,50,50,50);
        sb.end();
    }

    @Override
    public void dispose() {
        backround.dispose();
        playBtn.dispose();
    }
}
