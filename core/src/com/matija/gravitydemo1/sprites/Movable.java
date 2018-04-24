package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by Korisnik on 21.4.2018..
 */

public interface Movable {

    public Vector2 getPosition();
    public Vector2 getVelocity();
    public Vector2 getAcceleration();
}
