package com.matija.gravitydemo1.states;

import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Korisnik on 21.5.2018..
 */

public class SecondLoadingState extends LoadingState {

    /**
     * Konstruktor
     *
     * @param gsm Upravljač stanjima
     */
    public SecondLoadingState(GameStateManager gsm) {
        super(gsm);
        this.load();
    }

    @Override
    protected void load() {


        for (int i=0;i<200;i++){
            Assets.manager.load(Assets.weirdPlanet.get(i),Texture.class);
        }

        for (int i=0;i<50;i++){
            Assets.manager.load(Assets.moon.get(i),Texture.class);
        }
    }

    @Override
    protected void nextState() {
        gsm.set(new PlayState(gsm));
    }
}
