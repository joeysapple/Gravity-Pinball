package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by luka on 17.05.18..
 */

public class DisassemblingBackground implements Drawable,Movable {


    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private float thrust;

    private Texture background;


    public DisassemblingBackground(int x, int y){
        this.position = new Vector2( x, y);
        this.velocity = new Vector2(0 ,0);
        this.background = new Texture("LukinAsset/background5.png");

        //Smooth
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        thrust = 0;

    }

    public void update (float dt){
        velocity.add(0, thrust);
        velocity.scl(dt);
        position.add(0,velocity.y);
        velocity.scl(1/dt);
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

    public void setThrust(float thrust) {
        this.thrust = thrust;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    @Override
    public void draw(SpriteBatch sb) {
   }

    @Override
    public void dispose() {
        background.dispose();
    }

    public Texture getBackground() {
       return background;
    }
    public void setBackground(Texture background) {
        this.background = background;
    }
}