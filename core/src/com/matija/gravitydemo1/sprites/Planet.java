package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Circle;
import com.matija.gravitydemo1.states.PlayState;

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
public class Planet {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private double mass;
    private Circle bounds;
    private int radius;
    private Sprite planetSprite;

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
        Texture texture = new Texture(Gdx.files.internal("jupiter.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        planetSprite = new Sprite(texture);
        radius = 75;
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

    public Sprite getPlanetSprite() {
        return planetSprite;
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

    public void setTexture(Sprite t){
        this.planetSprite = t;
    }


}
