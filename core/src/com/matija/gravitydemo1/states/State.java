package com.matija.gravitydemo1.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Korisnik on 23.3.2018..
 */

/**
 * Abstraktni razred koji predstavlja stanje u igri.
 */
public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;

    /**
     * Konstruktor
     * @param gsm Upravljač stanjima
     */
    public State(GameStateManager gsm){
        this.gsm=gsm;
        this.cam = new OrthographicCamera();
        this.mouse=new Vector3();
    }

    /**
     * Medota koja obrađuje ulaze (npr. korisnikov pritisak na određenu tipku)
     */
    public abstract void handleInput();

    /**
     * Metoda koja se poziva da bi se stanje ažuriralo
     * @param dt Vrijeme proteklo od zadnjeg ažuriranja
     */
    public abstract void update(float dt);

    /**
     * Metoda za renderiranje
     * @param sb SpriteBatch
     */
    public abstract void render(SpriteBatch sb);

    /**
     * Metoda za otpuštanje svih resursa koja se treba pozvati prije nego što igra
     * prestane koristiti ovo stanje, tj. prije nego što game state manager skine stanje
     * sa stoga.
     */
    public abstract  void dispose();


}
