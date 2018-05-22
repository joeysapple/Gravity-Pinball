package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by luka on 18.05.18..
 */

public class Part implements Drawable,Movable {
    private Vector2 position;
    private Vector2 velocity;
    private Texture transporter;
    private float gravity = 0;
    private boolean released = false;

    public Part(int x, int y){
        this.position = new Vector2( x, y);
        this.velocity = new Vector2(0 ,0);
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
        return null;
    }

    @Override
    public void draw(SpriteBatch sb) {
    }

    public void setTexture(Texture transporter) {
        this.transporter = transporter;
        this.transporter.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    @Override

    public void dispose() {
        transporter.dispose();
    }

    public Texture getTexture() {
        return transporter;
    }

    public void update (float dt){
        velocity.add(0, gravity);
        velocity.scl(dt);
        position.add(0,velocity.y);
        velocity.scl(1/dt);
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public void setGravity(float gravity) {
        this.gravity = gravity;
    }

    public boolean isReleased() {
        return released;
    }

    public void setReleased(boolean released) {
        this.released = released;
    }
}