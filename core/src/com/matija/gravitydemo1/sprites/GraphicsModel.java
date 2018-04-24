package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import javax.naming.OperationNotSupportedException;

/**
 * Created by Korisnik on 21.4.2018..
 */

public abstract class GraphicsModel {

    protected Texture texture;
    protected Sprite sprite;

    public GraphicsModel(String file){
        texture = new Texture(Gdx.files.internal(file));
    }

    public void dispose(){
        texture.dispose();
    }
    public abstract void changeState() throws OperationNotSupportedException;



}
