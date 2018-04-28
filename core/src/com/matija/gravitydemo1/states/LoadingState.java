package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.matija.gravitydemo1.GravityDemo1;

/**
 * Created by Korisnik on 28.4.2018..
 */

public class LoadingState extends State {

    private boolean loading = false;
    private BitmapFont font = new BitmapFont();
    private ProgressBar pb;
    private Sprite backroundSprite;
    private Texture background;
    private Pixmap loadingBarBackground;
    private Pixmap loadingBar;
    private Texture t;
    /**
     * Konstruktor
     *
     * @param gsm Upravljač stanjima
     */
    public LoadingState(GameStateManager gsm) {
        super(gsm);

        cam.setToOrtho(false, GravityDemo1.WIDTH/2,GravityDemo1.HEIGHT/2);
        t = new Texture(Gdx.files.internal("dot.jpg"));
        background = new Texture(Gdx.files.internal("space.jpg"));
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backroundSprite = new Sprite(background);

        loadingBarBackground = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        loadingBarBackground.setColor(Color.GREEN);
        loadingBarBackground.fill();

        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(new Texture(loadingBarBackground)));

        ProgressBar.ProgressBarStyle progressBarStyle = new ProgressBar.ProgressBarStyle();
        progressBarStyle.background = drawable;
        loadingBarBackground.dispose();


        Pixmap pixmap1 = new Pixmap(0, 20, Pixmap.Format.RGBA8888);
        pixmap1.setColor(Color.GREEN);
        pixmap1.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap1)));

        progressBarStyle.knob=new TextureRegionDrawable(new TextureRegion(new Texture(pixmap1)));
        pixmap1.dispose();

        Pixmap pixmap2 = new Pixmap(100, 20, Pixmap.Format.RGBA8888);
        pixmap2.setColor(Color.GREEN);
        pixmap2.fill();
        drawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap2)));


        progressBarStyle.knobBefore = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap2)));
        pixmap2.dispose();

        pb = new ProgressBar(0.0f,1.0f, (float) 0.01,false,progressBarStyle);
        pb.setAnimateDuration(0.25f);
        pb.setBounds(50,50,100,20);
        pb.setValue(0.5f);

        load();
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void update(float dt) {
      if(Assets.manager.update()){
          this.dispose();
          gsm.set(new PlayState(gsm));
      }
    }

    @Override
    public void render(SpriteBatch sb) {
        float progress = Assets.manager.getProgress();
        System.out.println(progress);

        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        backroundSprite.draw(sb);
        sb.draw(t,50,200,100,20);
        pb.setValue(progress);
        System.out.println("Procent: "+pb.getPercent());
        pb.setBounds(50,200,100*progress,20);
        pb.draw(sb,1);
        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        t.dispose();

    }

    private void load(){
        Assets.manager.load(Assets.rocket,Texture.class);
        Assets.manager.load(Assets.jupiter,Texture.class);

        for (int i=0;i<36;i++){
            Assets.manager.load(Assets.earth.get(i),Texture.class);
        }
    }
}
