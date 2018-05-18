package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Text extends Actor {

    BitmapFont font;
    String data;
    int x;
    int y;

    public Text(String data, FreeTypeFontGenerator fontGen,
                FreeTypeFontGenerator.FreeTypeFontParameter parameter, int x, int y){
        font = fontGen.generateFont(parameter);
        this.data = data;
        this.x = x;
        this.y = y;

    }

    public void setText(String text){
        data = text;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        font.draw(batch, data, x, y);
        //Also remember that an actor uses local coordinates for drawing within
        //itself!
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        // TODO Auto-generated method stub
        return null;
    }

}