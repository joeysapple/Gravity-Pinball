package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.matija.gravitydemo1.GravityDemo1;

/**
 * Created by Korisnik on 28.4.2018..
 */

public class FirstLoadingState extends LoadingState {

    /**
     * Konstruktor
     *
     * @param gsm Upravljač stanjima
     */
    public FirstLoadingState(GameStateManager gsm) {
        super(gsm);
        Assets.newManager();
       // Assets.newManager();
        load();
    }

 /*   @Override
    public final void handleInput() {

    }

    @Override
    public final void update(float dt) {
      if(Assets.manager.update() && progress>=1.0f){
          this.dispose();
          gsm.set(new MenuState(gsm));
      }

    }

    @Override
    public final void render(SpriteBatch sb) {
        progress = Assets.manager.getProgress();
     //   System.out.println(progress);

        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        backroundSprite.draw(sb);
        progressBar.setProgressValue(progress);
        progressBar.draw(sb);
        /*
        sb.draw(t,50,200,150,20);
        pb.setValue(progress);
        System.out.println("Procent: "+pb.getPercent());
        pb.setBounds(50,200,150*progress,20);
        pb.draw(sb,1);

        sb.end();

    }

    @Override
    public  void dispose() {
        background.dispose();
        progressBar.dispose();

    }*/

    @Override
    protected void load(){
        Assets.manager.load(Assets.rocket,Texture.class);
        Assets.manager.load(Assets.jupiter,Texture.class);
        Assets.manager.load("sphere.png",Texture.class);
        Assets.manager.load(Assets.background,Texture.class);
        Assets.manager.load(Assets.redBackground,Texture.class);
        Assets.manager.load(Assets.sound, Music.class);

       /* FileHandleResolver resolver = new InternalFileHandleResolver();
        Assets.manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        Assets.manager.setLoader(BitmapFont.class, ".TTF", new FreetypeFontLoader(resolver));*/

      //  Assets.manager.load(Assets.textGen, BitmapFont.class);

        Assets.manager.load(Assets.touchpadBg,Texture.class);
        Assets.manager.load(Assets.touchKnob, Texture.class);
        Assets.loadFont();

        for (int i=0;i<200;i++){
            Assets.manager.load(Assets.earth.get(i),Texture.class);
        }


    /*    for (int i=0;i<200;i++){
            Assets.manager.load(Assets.weirdPlanet.get(i),Texture.class);
        }

        for (int i=0;i<50;i++){
            Assets.manager.load(Assets.moon.get(i),Texture.class);
        }*/
    }

    @Override
    protected void nextState() {
        gsm.set(new MenuState(gsm));
    }
}
