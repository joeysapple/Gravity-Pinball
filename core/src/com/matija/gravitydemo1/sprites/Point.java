package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Korisnik on 3.5.2018..
 */

public class Point implements Drawable{
    public Vector2 position;
    private GraphicsModel model;
    private Circle bound;
    private int radius=2;

    public Point(Vector2 position){
        this.position=position;
        model = new SpriteGraphicsModel("sphere.png");
        bound = new Circle(position.x,position.y,radius);
        model.sprite.setSize(5,5);
    }

    @Override
    public void draw(SpriteBatch sb) {
       // model.sprite.setPosition(position.x,position.y);
        model.sprite.setCenter(position.x,position.y);
        model.sprite.draw(sb);
    }

    @Override
    public void dispose() {
        model.dispose();
    }


    public Circle getBounds(){
        return bound;
    }

    public void setPosition(float x,float y){
        position.x=x;
        position.y=y;

        bound.setPosition(x,y);
    }

    public Vector2 getPosition(){
        return position;
    }


}
