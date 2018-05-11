package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.matija.gravitydemo1.states.Menus;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;

import java.awt.Label;
import java.awt.TextField;
import java.util.Map;

/**
 * Created by mikul on 5.4.2018..
 */

public class HighState extends State{
    private Texture background;
    private Texture backbtn;
    private Texture clrBtn;
    private Texture title;
    private BitmapFont font;
    private FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Comic_sans.ttf"));
    FreeTypeFontParameter parameter = new FreeTypeFontParameter();

    private Rectangle boundBack;
    private Rectangle boundClear;

    Preferences pref = Gdx.app.getPreferences("Highscore");





    public HighState(GameStateManager gsm) {
        super(gsm);
        //za mob
        cam.setToOrtho(false, Menus.WIDTH/2, Menus.HEIGHT /2);
        background = new Texture("background.png");

        backbtn = new Texture("button_back.png");
        title = new Texture("highscore1.png");
        clrBtn = new Texture("button_clear.png");

        boundBack=new Rectangle(cam.position.x*1.05f, cam.position.y*0.25f-backbtn.getHeight(),backbtn.getWidth(),backbtn.getHeight());
        boundClear=new Rectangle(cam.position.x - clrBtn.getWidth()*1.05f, cam.position.y*0.25f-clrBtn.getHeight(),clrBtn.getWidth(),clrBtn.getHeight());

        parameter.size=30;
        parameter.color= Color.WHITE;
        parameter.borderColor= Color.BLUE;
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

            if(boundClear.contains(tmp.x,tmp.y)) {
                for( int i = 0; i < 5; i++) {
                    pref.remove(Integer.toString(i+1));
                    pref.flush();
                }
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

        for( int i = 0; i < 5; i++) {
            font.draw(sb,Integer.toString(i+1) +". " +pref.getString(Integer.toString(i+1), "aaa:0"), cam.position.x*0.5f, cam.position.y+(2-i)*font.getLineHeight()*0.75f);
        }




        sb.draw(backbtn, cam.position.x*1.05f , cam.position.y*0.25f-backbtn.getHeight());
        sb.draw(clrBtn, cam.position.x - clrBtn.getWidth()*1.05f, cam.position.y*0.25f-clrBtn.getHeight());

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        backbtn.dispose();
        title.dispose();
        font.dispose();

    }
}
