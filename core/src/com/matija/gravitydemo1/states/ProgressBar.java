package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.matija.gravitydemo1.GravityDemo1;
import com.matija.gravitydemo1.sprites.Drawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Korisnik on 30.4.2018..
 */

public class ProgressBar implements Drawable {

    private Vector2 position;
    private int width;
    private Texture rocket;
    private Texture mark;
    private float progress;
    private int currentX;
    public List<Integer> marks;
    private int rocketHeigth,rocketWidth;
    private int markHeigth,markWidth;
    int currentIndex;
    private BitmapFont font;

    public ProgressBar(Vector2 position,int width){
       this.position=position;
       this.width=width;
       rocket = new Texture(Gdx.files.internal("rocketRight.png"));
       mark = new Texture(Gdx.files.internal("sphere.png"));
       mark.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
       marks = new ArrayList<Integer>();
       rocketHeigth=rocketWidth=40;
       markHeigth=markWidth=5;
       currentIndex = 0;
       font = new BitmapFont();
       font.setColor(Color.GOLD);
       font.getData().setScale(0.8f);

       calculateX();
       marksInit();
    }

    @Override
    public void draw(SpriteBatch sb) {

        if (currentX>=marks.get(currentIndex)){
            currentIndex = currentIndex<marks.size()?currentIndex+1:9;
        }
        for (int i=currentIndex;i<marks.size();i++){
           sb.draw(mark,marks.get(i),position.y-markHeigth/2,markHeigth,markWidth);
        }
        sb.draw(rocket,currentX-rocketWidth,position.y-rocketWidth/2,rocketHeigth,rocketWidth);
        font.draw(sb,(int)(progress*100)+" %", GravityDemo1.WIDTH/4,position.y-30);

    }

    @Override
    public void dispose() {
        mark.dispose();
        rocket.dispose();
        font.dispose();
    }

    public void setProgressValue(float progress){
        if (progress<0.0f || progress>1.0f){
            throw new IllegalArgumentException("Progress value must be in interval [0,1]");
        }
        this.progress=progress;
        calculateX();
    }

    private void calculateX(){
        currentX = (int) (width*progress+position.x);
    }

    private void marksInit(){
        int partLength = width/10;

        for (int i=1;i<=10;i++){
            marks.add((int) (position.x+partLength*i));
        }
    }

}
