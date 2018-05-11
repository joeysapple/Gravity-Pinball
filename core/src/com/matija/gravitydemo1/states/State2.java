package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.matija.gravitydemo1.states.Menus;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by mikul on 5.4.2018..
 */

public class State2 extends State{
    private Texture background;
    private Texture playBtn;
    private Rectangle boundPlay;
    public State2(GameStateManager gsm) {
        super(gsm);
        //za mob
        cam.setToOrtho(false, Menus.WIDTH/2, Menus.HEIGHT /2);
        background = new Texture("background.png");
        playBtn = new Texture("button_back.png");

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){

            Vector3 tmp=new Vector3( Gdx.input.getX(), Gdx.input.getY(),0);
            cam.unproject(tmp);
            Rectangle textureBounds=new Rectangle(cam.position.x - playBtn.getWidth() / 2, cam.position.y,playBtn.getWidth(),playBtn.getHeight());

            if(textureBounds.contains(tmp.x,tmp.y)) {
                gsm.set(new MenuState(gsm));
            }

        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0, Menus.WIDTH/2, Menus.HEIGHT /2);

        sb.draw(playBtn, cam.position.x - playBtn.getWidth()/2, cam.position.y);

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();

    }
}
