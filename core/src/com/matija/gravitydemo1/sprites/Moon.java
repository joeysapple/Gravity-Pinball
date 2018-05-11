package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

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
        model = new SpriteGraphicsModel("jupiter.png");


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
        sb.draw(model.texture, position.x-radius, position.y-radius,2*radius,2*radius);
    }

    @Override
    public void dispose() {
        model.dispose();
    }
}
