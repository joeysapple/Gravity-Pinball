package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import javax.naming.OperationNotSupportedException;

/**
 * Created by Korisnik on 21.4.2018..
 */

public class SpriteGraphicsModel extends GraphicsModel {

    private Sprite sprite;

    public SpriteGraphicsModel(String file){
        super(file);
        filterTexture();
        sprite = new Sprite(texture);
    }

    @Override
    public void changeState() throws OperationNotSupportedException {
        throw new OperationNotSupportedException("Sprite model cannot change state");
    }


    private void filterTexture(){
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }
}
