package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.matija.gravitydemo1.states.Assets;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;


/**
 * Created by Korisnik on 21.4.2018..
 */

public class GifGraphicsModel extends GraphicsModel {

    List<Texture> textures;
    List<Sprite> sprites;
    private double counter;
    private double currentMil;
    private int current;
    private int size;
    public GifGraphicsModel(String file, int size) {
        super(file);
        textures = new ArrayList<Texture>();
        sprites = new ArrayList<Sprite>();

        StringBuilder fileSB = new StringBuilder(file);
        int index = fileSB.lastIndexOf("/");
        String path = fileSB.substring(0,index+1);
        current = 0;
        counter = 0;
        for (int i=0;i<size;i++){
            StringBuilder sb = new StringBuilder(path);
            sb.append("frame (" + (i+1) + ").png");
            System.out.println("Load "+sb.toString());
           // Texture t = new Texture(Gdx.files.internal(sb.toString()));
            Texture t = Assets.manager.get(sb.toString(),Texture.class);

            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textures.add(t);
            Sprite s = new Sprite(t);
            sprites.add(s);


        }
        currentMil = System.currentTimeMillis();
        this.size = size;
        sprite = sprites.get(0);
    }

    @Override
    public void changeState() throws OperationNotSupportedException {
        double currentM = System.currentTimeMillis();
        counter+=(currentM - currentMil);
        currentMil = currentM;

        if (counter>75){
            counter = 0;
            current++;
            current = current%this.size;
            texture = textures.get(current);
            sprite = sprites.get(current);
        }
    }


   @Override
    public void dispose(){
        super.dispose();
        for (Texture t:textures){
           // t.dispose();
        }

   }


}
