package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Circle;
import com.matija.gravitydemo1.states.PlayState;

import javax.naming.OperationNotSupportedException;

/**
 * Created by Korisnik on 29.3.2018..
 */

/**
 * Ova klasa modelira planet. Položaj, brzina i akceleracija sadržane su u varijablama position, velocity i acceleration.
 * U pravilu brzina i akceleracija planeta su 0, budući da su planeti fiksirani. Masa planeta sadržana je u varijabli mass,
 * a granica planeta opisana je varijablom bounds tipa Circle. Planet ima radijus opisan varijablom radius, a grafički prikaz
 * planeta nalazi se u varijabli planetSprite.
 *
 */
public class Planet implements Drawable{
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private double mass;
    private Circle bounds;
    private int radius;
    private GraphicsModel model;
    /**
     * Konstruktor za planet
     * @param position Pozicija planeta
     * @param velocity Brzina planeta, u pravilu 0
     * @param acceleration Akceleracija planeta, u pravilu 0
     * @param mass Masa planeta
     */
    public Planet(Vector2 position,Vector2 velocity,Vector2 acceleration, double mass){
        this.position = new Vector2(position.x,position.y);
        this.velocity = new Vector2(velocity.x,velocity.y);
        this.acceleration = new Vector2(acceleration.x,acceleration.y);
        this.mass = mass;
       // model = new SpriteGraphicsModel("earth.gif");
        model = new GifGraphicsModel("earth/frame1.gif",36);
        radius = 190;
        //   circle = new Sprite("rocket.png");
        bounds = new Circle(position.x,position.y,radius);
    }

    /*
    Getteri i setteri
     */
    public Vector2 getPosition() {
        return position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public int getRadius() {
        return radius;
    }

    public Circle getBounds() {
        return bounds;
    }

    public double getMass() {
        return mass;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public void setAcceleration(float x,float y) {
        this.acceleration.x = x;
        this.acceleration.y = y;
    }


    @Override
    public void draw(SpriteBatch sb) {
       // sb.draw(model.texture,position.x-250, position.y-250 ,500,500);
        model.sprite.setSize(500,500);
        model.sprite.setCenter(position.x,position.y);
        model.sprite.draw(sb);
        try {
            model.changeState();
        } catch (OperationNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose(){
        model.dispose();
    }
}
