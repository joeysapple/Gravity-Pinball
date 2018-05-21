package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.matija.gravitydemo1.GravityDemo1;

/**
 * Created by Korisnik on 21.5.2018..
 */

public abstract class LoadingState extends State {

    protected boolean loading = false;
    protected BitmapFont font = new BitmapFont();
    protected com.badlogic.gdx.scenes.scene2d.ui.ProgressBar pb;
    protected Sprite backroundSprite;
    protected Texture background;
    protected Pixmap loadingBarBackground;
    protected Pixmap loadingBar;
    protected Texture t;
    protected float progress;
    protected com.matija.gravitydemo1.states.ProgressBar progressBar;
    /**
     * Konstruktor
     *
     * @param gsm Upravljač stanjima
     */
    public LoadingState(GameStateManager gsm) {
        super(gsm);
       // Assets.newManager();
        cam.setToOrtho(false, GravityDemo1.WIDTH/2,GravityDemo1.HEIGHT/2);
        progressBar = new com.matija.gravitydemo1.states.ProgressBar(new Vector2(50,200),150);

        t = new Texture(Gdx.files.internal("dot.jpg"));
        background = new Texture(Gdx.files.internal("space.jpg"));

        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backroundSprite = new Sprite(background);


      //  load();
    }

    @Override
    public final void handleInput() {

    }

    @Override
    public final void update(float dt) {
        if(Assets.manager.update() && progress>=1.0f){
            this.dispose();
            this.nextState();
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
        */
        sb.end();

    }

    @Override
    public final void dispose() {
        background.dispose();
        progressBar.dispose();

    }

    protected abstract void load();

    protected abstract void nextState();

}
