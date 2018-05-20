package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.math.Circle;
import com.matija.gravitydemo1.states.PlayState;

import java.util.ArrayList;
import java.util.List;

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
    private List<GraphicsModel> models;
    private int currentModel;
    private int numberOfModels=2;
    /**
     * Konstruktor za planet
     * @param position Pozicija planeta
     * @param velocity Brzina planeta, u pravilu 0
     * @param acceleration Akceleracija planeta, u pravilu 0
     * @param mass Masa planeta
     */
    public Planet(Vector2 position,Vector2 velocity,Vector2 acceleration, double mass, int radius){
        this.models = new ArrayList<GraphicsModel>();
        this.position = new Vector2(position.x,position.y);
        this.velocity = new Vector2(velocity.x,velocity.y);
        this.acceleration = new Vector2(acceleration.x,acceleration.y);
        this.mass = mass;
       // model = new SpriteGraphicsModel("earth.gif");
        model = new GifGraphicsModel("weirdPlanet/frame (1).png",50);
        models.add(model);

        model = new GifGraphicsModel("earth/frame (1).png",50);
        models.add(model);
        //model = new GifGraphicsModel("jupiter2/frame_00.gif", 54);
        this.radius = radius;
        //   circle = new Sprite("rocket.png");
        bounds = new Circle(position.x,position.y,radius);
        currentModel=1;
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

    public void setPosition(Vector2 position) {
        this.position = new Vector2(position.x,position.y);
    }

    public void setPosition(float x, float y) {
        this.position.x = x;
        this.position.y = y;
    }

    public void setRadius(int rad) {
        this.radius = rad;
    }

    public void setMass(int masa) {
        this.mass = masa;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public void setAcceleration(float x,float y) {
        this.acceleration.x = x;
        this.acceleration.y = y;
    }

    public void setBounds() {
        bounds = new Circle(position.x,position.y,radius);
    }


    @Override
    public void draw(SpriteBatch sb) {
       // sb.draw(model.texture,position.x-250, position.y-250 ,500,500);
        model.sprite.setSize(radius*2,radius*2);

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

    public void setOneOfModel(int n){
        model = models.get(n);
    }

    public void setNextModel(){
        currentModel++;
        currentModel%=numberOfModels;
        model=models.get(currentModel);
    }
}
