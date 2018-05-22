package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.matija.gravitydemo1.GravityDemo1;
import com.matija.gravitydemo1.states.PlayState;

import javax.naming.OperationNotSupportedException;

/**
 * Created by Korisnik on 24.4.2018..
 */

public class Moon implements Drawable,Movable {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private double mass;
    private int radius;
    private Circle bounds;
    private GraphicsModel model;

    public Moon(Vector2 position,Vector2 velocity,Vector2 acceleration, double mass){
        this.position = new Vector2(position.x,position.y);
        this.velocity = new Vector2(velocity.x,velocity.y);
        this.acceleration = new Vector2(acceleration.x,acceleration.y);
        this.mass = mass;
        radius = 25;
        bounds = new Circle(position.x,position.y,radius);
        model = new GifGraphicsModel("moon/frame (1).png",50);



    }
    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public Vector2 getAcceleration() {
        return acceleration;
    }

    @Override
    public void draw(SpriteBatch sb) {
       // sb.draw(model.texture, position.x-radius, position.y-radius,2*radius,2*radius);
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
    public void dispose() {
        model.dispose();
    }

    public void reposition(Planet planet){
        int distance = planet.getRadius()*2;
        this.radius = planet.getRadius()/4;

        if (distance-planet.getRadius()-this.radius<60){
            distance = 60+planet.getRadius()+this.radius;
        }
        this.position.set(planet.getPosition().x+distance,planet.getPosition().y);
        int velocity = (int)Math.sqrt(PlayState.G*planet.getMass()/distance);

        this.velocity.set(0,-velocity);
        this.bounds.radius = radius;
    }

    public void setBounds(){
        this.bounds.setPosition(position.x,position.y);
    }

    public Circle getBounds(){
        return this.bounds;
    }

    public Texture getModel(){
        return this.model.texture;
    }
}
