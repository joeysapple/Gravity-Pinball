package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.matija.gravitydemo1.states.Menus;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by mikul on 5.4.2018..
 */

public class HelpState extends State{
    private Texture background;
    private Texture backBtn;
    private Texture title;
    private Rectangle boundBack;

    private BitmapFont font;
    private FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Comic_sans.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    private String text = "Use the touchpad to\nnavigate your rocket\nagainst the planet's\ngravity. Once you clear\nthe planet's gravity" +
            "\nfield you get 5 points. \nIf you manage to pass \nbetween the planet and \nit's satellite you get \nadditional 20 points. In\norder" +
            "to win the game you\nhave to clear 10 planets.";


    public HelpState(GameStateManager gsm) {
        super(gsm);
        //za mob
        cam.setToOrtho(false, Menus.WIDTH/2, Menus.HEIGHT /2);
        background = new Texture("background.png");
        title = new Texture("help.png");
        backBtn = new Texture("button_back.png");
        boundBack=new Rectangle(cam.position.x - backBtn.getWidth() / 2, cam.position.y*0.15f,backBtn.getWidth(),backBtn.getHeight());

        parameter.size=15;
        parameter.color= Color.WHITE;
        parameter.borderColor= Color.BLACK;
        parameter.borderWidth = 1;
        font = fontGen.generateFont(parameter);
        fontGen.dispose();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){

            Vector3 tmp=new Vector3( Gdx.input.getX(), Gdx.input.getY(),0);
            cam.unproject(tmp);
            //Rectangle textureBounds=new Rectangle(cam.position.x - playBtn.getWidth() / 2, cam.position.y,playBtn.getWidth(),playBtn.getHeight());

            if(boundBack.contains(tmp.x,tmp.y)) {
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
        sb.draw(title, cam.position.x +7 - title.getWidth() / 2, cam.position.y*2f - title.getHeight()*1.1f);
        font.draw(sb, text, 25, cam.position.y*2f - title.getHeight()*1.3f);
        sb.draw(backBtn, cam.position.x - backBtn.getWidth()/2, cam.position.y*0.15f);

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        backBtn.dispose();
        title.dispose();

    }
}
