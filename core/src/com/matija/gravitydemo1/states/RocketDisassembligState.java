package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.matija.gravitydemo1.GravityDemo1;
import com.matija.gravitydemo1.sprites.DisassemblingBackground;
import com.matija.gravitydemo1.sprites.Part;
import com.matija.gravitydemo1.sprites.Rocket;
import com.matija.gravitydemo1.sprites.RocketTransporter;

import java.io.DataInputStream;
import java.util.Collections;

/**
 * Created by luka on 12.05.18..
 */

public class RocketDisassembligState extends State{
    private DisassemblingBackground background;
    private boolean crushed = false;
    private boolean notGonnaFall = true;
    private float startingPosition;
    private int brojSlike = 0;
    private int jacinaMlaza = 0;

    private Sound successSound;
    private Sound explosionSound;
    private Music music;

    private boolean explosionPlayed = false;
    private boolean musicStopped = false;

    private boolean lastPart = false;
    private Part[] parts = {new Part(0,0),new Part(0,0),new Part(0,0),new Part(0,0)};
    private Part part;
    private Texture[][] transportersTextures = {{new Texture("LukinAsset/transporter11.png"),
                                         new Texture("LukinAsset/transporter12.png"),
                                         null, null
                                        },
                                        {new Texture("LukinAsset/transporter21.png"),
                                         new Texture("LukinAsset/transporter22.png"),
                                         new Texture("LukinAsset/transporter23.png"),
                                         new Texture("LukinAsset/transporter24.png")
                                        },
                                        {new Texture("LukinAsset/transporter31.png"),
                                         new Texture("LukinAsset/transporter32.png"),
                                         new Texture("LukinAsset/transporter33.png"),
                                         new Texture("LukinAsset/transporter34.png")
                                        },
                                        {new Texture("LukinAsset/transporter41.png"),
                                         new Texture("LukinAsset/transporter42.png"),
                                         new Texture("LukinAsset/transporter43.png"),
                                         new Texture("LukinAsset/transporter44.png")
                                        },
                                        {new Texture("LukinAsset/onlyRocket.png"),
                                         new Texture("LukinAsset/onlyRocket.png"),
                                         null,null
                                         },
                                       };
    private RocketTransporter[][] transporters =
            {{new RocketTransporter(0,0),
                    new RocketTransporter(0,0),
                    null, null
                     },
                    {new RocketTransporter(0,0),
                            new RocketTransporter(0,0),
                            new RocketTransporter(0,0),
                            new RocketTransporter(0,0),
                    },
                    {new RocketTransporter(0,0),
                            new RocketTransporter(0,0),
                            new RocketTransporter(0,0),
                            new RocketTransporter(0,0),
                    },
                    {new RocketTransporter(0,0),
                            new RocketTransporter(0,0),
                            new RocketTransporter(0,0),
                            new RocketTransporter(0,0),
                    },
                    {new RocketTransporter(0,0),
                            new RocketTransporter(0,0),
                            null,null
                    },
            };

    private RocketTransporter transporter;

    private int partNumber = 0;
    private boolean success = false;
    private Texture gasButton = new Texture("LukinAsset/button_gas.png");
    private Texture releaseButton = new Texture("LukinAsset/button_release.png");
    private boolean enableReleaseBtn;

    private Rectangle boundGas, boundRelease;



    public RocketDisassembligState(GameStateManager gsm) {
        super(gsm);

        successSound = Assets.manager.get(Assets.success,Sound.class);
        explosionSound = Assets.manager.get(Assets.explosion,Sound.class);
        music = Assets.manager.get(Assets.sound,Music.class);

        music.setLooping(true);
        music.setVolume(1.0f);
        music.play();

        background = new DisassemblingBackground(0,0);

        System.out.println("visina:" + (int) Math.round(background.getBackground().getHeight()/1.7));
        transporter = transporters[0][0];
        cam.setToOrtho(false, background.getBackground().getWidth(),1150);
        startingPosition = background.getPosition().y;
        boundGas = new Rectangle(cam.position.x *2 - gasButton.getWidth() * 3, cam.position.y*1.25f,gasButton.getWidth()*2,gasButton.getHeight()*2);
        boundRelease = new Rectangle(cam.position.x *2 - gasButton.getWidth() * 3, cam.position.y,gasButton.getWidth()*2,gasButton.getHeight()*2);
        parts[0].setTexture(new Texture("LukinAsset/part1.png"));
        parts[1].setTexture(new Texture("LukinAsset/part2.png"));
        parts[2].setTexture(new Texture("LukinAsset/part3.png"));
        parts[3].setTexture(new Texture("LukinAsset/part4.png"));
        part = parts[0];

        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 4; j++){
                try {
                    transporters[i][j].setTexture(transportersTextures[i][j]);
                }catch(NullPointerException ex){}

            }
        }

    }

    @Override
    public void handleInput() {

        if(Gdx.input.isTouched() && !success ){
            Vector3 tmp=new Vector3( Gdx.input.getX(), Gdx.input.getY(),0);
            cam.unproject(tmp);
            if(boundRelease.contains(tmp.x,tmp.y) && enableReleaseBtn) {
                //part = parts[partNumber];
                part.setGravity(-3);
                transporter = transporters[brojSlike][0];
                enableReleaseBtn = false;
                part.setReleased(true);
                if(lastPart){
                    transporter.setThrust(5);
                    background.setThrust(1);
                    success = true;
                    successSound.play(0.5f);
                }
            }
            else if(boundGas.contains(tmp.x,tmp.y) && !enableReleaseBtn) {
                if (notGonnaFall) {
                    background.setThrust((float) -0.5);
                }
                transporter = transporters[brojSlike][1];
            }
            else{background.setThrust((float) 0.5);
                transporter = transporters[brojSlike][0];
            }
        }
        else if(background.getPosition().y < startingPosition && !success){
            background.setThrust((float) 0.5);
            transporter = transporters[brojSlike][0];
        }

        if(crushed){
            if(Gdx.input.isTouched()){
                gsm.set(new GameOverState(gsm, 0, false));
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
        background.update(dt);
        try{
            part.update(dt);
        }catch(Exception ex){}
        try{
            transporter.update(dt);
            if(transporter.getPosition().y > 1000) {
                gsm.set(new PlayState(gsm));

            }
        }catch(NullPointerException ex){}

        if(background.getPosition().y > startingPosition) {
            background.setThrust((float) 0);
            background.setPosition(new Vector2(0, 0));
           // background.dispose();
            transporter.setTexture(new Texture("LukinAsset/explosion4.png"));
            if (!musicStopped){
                musicStopped=true;
                music.stop();
            }
            crushed = true;
            if (!explosionPlayed){
                explosionSound.play(0.5f);

                explosionPlayed=true;
            }

            //cam.setToOrtho(false, background.getBackground().getWidth(), background.getBackground().getHeight() / 1);
        }

       // System.out.println("pozicija: " + background.getPosition().y);
        if(background.getPosition().y < -500 && background.getPosition().y > -550  && notGonnaFall && !part.isReleased()){
            brojSlike = 1;
            part = parts[0];
            enableReleaseBtn = true;
            Gdx.input.vibrate(100);
        }
        if(background.getPosition().y < -1200  && notGonnaFall && part.isReleased())
            part = parts[1];
        if(background.getPosition().y < -1350 && background.getPosition().y > -1400  && notGonnaFall && !part.isReleased()){
            brojSlike = 2;
            partNumber = 1;
            enableReleaseBtn = true;
            Gdx.input.vibrate(100);
        }
        if(background.getPosition().y < -2000  && notGonnaFall && part.isReleased())
            part = parts[2];
        if(background.getPosition().y < -2100 && background.getPosition().y > -2150  && notGonnaFall && !part.isReleased()){
            brojSlike = 3;
            partNumber = 2;
            enableReleaseBtn = true;
            Gdx.input.vibrate(100);
        }

        if(background.getPosition().y < -2900  && notGonnaFall && part.isReleased())
            part = parts[3];
        if(background.getPosition().y < -3050 && background.getPosition().y > -3100  && notGonnaFall && !part.isReleased()){
            brojSlike = 4;
            partNumber = 3;
            enableReleaseBtn = true;
            lastPart = true;
            Gdx.input.vibrate(100);

        }

        if(background.getPosition().y < -3850 && background.getPosition().y > -3900 && false){
            transporter.setThrust(5);
            background.setThrust(1);
            success = true;

        }






        if(background.getVelocity().y > background.getPosition().y/10 ){
            notGonnaFall = false;
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        sb.draw(background.getBackground(), background.getPosition().x, background.getPosition().y);
        if(!crushed) {
            sb.draw(part.getTexture(),
                    background.getBackground().getWidth() / 2 - part.getTexture().getWidth() / 2,
                    50 + part.getPosition().y);
        }
            sb.draw(transporter.getTexture(), background.getBackground().getWidth() / 2 - transporter.getTexture().getWidth() / 2, 50 + transporter.getPosition().y);
        /*if(crushed) {
            try {
                sb.end();
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gsm.set(new GameOverState(gsm, 0, false));
        }*/
        if(!crushed) {
            if(!success) {
                if (enableReleaseBtn) {
                    sb.draw(releaseButton, cam.position.x * 2 - releaseButton.getWidth() * 3, cam.position.y, releaseButton.getWidth() * 2, releaseButton.getHeight() * 2);
                } else {
                    sb.draw(gasButton, cam.position.x * 2 - gasButton.getWidth() * 3, cam.position.y * 1.25f, gasButton.getWidth() * 2, gasButton.getHeight() * 2);

                }
            }
            }

        sb.end();








    }

    @Override
    public void dispose() {
        background.dispose();
        transporter.dispose();
        releaseButton.dispose();
        gasButton.dispose();
     /*   if (!musicStopped){
            musicStopped=true;
            music.stop();
        }*/


    }

    private void load(){

    }
}




