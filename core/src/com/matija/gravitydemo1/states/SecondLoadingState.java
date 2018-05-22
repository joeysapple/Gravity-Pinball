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
    private boolean launch;

    public SecondLoadingState(GameStateManager gsm, boolean launch) {
        super(gsm);
        this.load();
        this.launch=launch;
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

        if (launch){
            gsm.set(new RocketDisassembligState(gsm));
        }
        else{
            gsm.set(new PlayState(gsm));
        }
    }
}
