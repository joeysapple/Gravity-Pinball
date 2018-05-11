package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Korisnik on 21.4.2018..
 */

public interface Drawable {

    public void draw(SpriteBatch sb);
    public void dispose();

}
