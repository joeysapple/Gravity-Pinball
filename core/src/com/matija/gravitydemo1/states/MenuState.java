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

public class MenuState extends State{
    private Texture background;
    private Texture title;
    private Texture playBtn, hsBtn, helpBtn, exitBtn;
    private Rectangle boundPlay, boundHigh, boundHelp, boundExit;
    public MenuState(GameStateManager gsm) {
        super(gsm);
        //za mob
        cam.setToOrtho(false, Menus.WIDTH/2, Menus.HEIGHT /2 );

        title = new Texture("menu2.png");
        background = new Texture("background.png");
        playBtn = new Texture("button_play.png");
        hsBtn = new Texture("button_highscore.png");
        helpBtn = new Texture("button_help.png");
        exitBtn = new Texture("button_exit.png");

        //Smooth
        title.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        playBtn.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        hsBtn.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        helpBtn.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        exitBtn.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        boundPlay=new Rectangle(cam.position.x - playBtn.getWidth() / 2, cam.position.y*1.25f,playBtn.getWidth(),playBtn.getHeight());
        boundHigh=new Rectangle(cam.position.x - hsBtn.getWidth() / 2, cam.position.y*1f,hsBtn.getWidth(),hsBtn.getHeight());
        boundHelp=new Rectangle(cam.position.x - helpBtn.getWidth() / 2, cam.position.y*0.75f,helpBtn.getWidth(),helpBtn.getHeight());
        boundExit=new Rectangle(cam.position.x - exitBtn.getWidth() / 2, cam.position.y*0.5f,exitBtn.getWidth(),exitBtn.getHeight());

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){

            Vector3 tmp=new Vector3( Gdx.input.getX(), Gdx.input.getY(),0);
            cam.unproject(tmp);
            //Rectangle textureBounds=new Rectangle(cam.position.x - playBtn.getWidth() / 2, cam.position.y,playBtn.getWidth(),playBtn.getHeight());

            if(boundPlay.contains(tmp.x,tmp.y)) {
                gsm.set(new PlayState(gsm));
            }
            if(boundHelp.contains(tmp.x,tmp.y)) {
                gsm.set(new HelpState(gsm));
            }
            if(boundHigh.contains(tmp.x,tmp.y)) {
                gsm.set(new HighState(gsm));
            }
            if(boundExit.contains(tmp.x,tmp.y)) {
                Gdx.app.exit();
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
        sb.draw(title, cam.position.x +7 - title.getWidth() / 2, cam.position.y*2f - title.getHeight()*1.1f);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y*1.25f);
        sb.draw(hsBtn, cam.position.x - hsBtn.getWidth() / 2, cam.position.y*1f);
        sb.draw(helpBtn, cam.position.x - helpBtn.getWidth() / 2, cam.position.y*0.75f);
        sb.draw(exitBtn, cam.position.x - exitBtn.getWidth() / 2, cam.position.y*0.5f);

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        hsBtn.dispose();
        helpBtn.dispose();
        exitBtn.dispose();
        title.dispose();

    }
}
