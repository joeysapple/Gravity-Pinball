package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.matija.gravitydemo1.states.Menus;



/**
 * Created by mikul on 5.4.2018..
 */

public class StartState extends State{
    private Texture background;
    private BitmapFont font = new BitmapFont();


    public StartState(GameStateManager gsm) {
        super(gsm);
        //za mob
        cam.setToOrtho(false, Menus.WIDTH/2, Menus.HEIGHT /2 );
        background = new Texture("font_ver1.png");
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new MenuState(gsm));


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
        font.setColor(Color.WHITE);
        String fontText = "<Touch to continue>";
        GlyphLayout layout = new GlyphLayout(); //dont do this every frame! Store it as member
        layout.setText(font, fontText);
        font.draw(sb, fontText, cam.viewportWidth/2 - layout.width/2, 20 );

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();

    }
}
