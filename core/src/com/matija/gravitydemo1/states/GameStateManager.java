package com.matija.gravitydemo1.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.matija.gravitydemo1.GravityDemo1;

import java.util.Stack;

/**
 * Created by Korisnik on 23.3.2018..
 */

/**
 * Klasa koja modelira upravljača stanjima. Svaka instanca igre u pravilu ima samo jedan upravljač stanjima.
 * Klasa sadrži stog stanja states
 */

public class GameStateManager {
    private Stack<State> states;
    public GravityDemo1 game;

    /**
     * Konstruktor
     */
    public GameStateManager(GravityDemo1 game){
        states = new Stack<State>();
        this.game=game;
    }

    /**
     * Metoda za stavljanje novog stanja na vrh stoga
     * @param state Stanje koje se želi staviti na vrh stoga
     */
    public void push(State state){
        states.push(state);
    }

    /**
     * Metoda za skidanje trenutnog stanja sa vrha stoga
     */
    public void pop(){
        states.pop().dispose();
    }

    /**
     * Metoda za uzimanje stanja sa vrha stoga i stavljanje novog, tj. "switch states"
     * @param state Novo stanje koje će biti na vrhu stoga
     */
    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    /**
     * Metoda za ažuriranje stanja na vrhu stoga
     * @param dt Vrijeme proteklo od zadnjeg ažuriranja
     */
    public void update(float dt){
        states.peek().update(dt);
    }

    /**
     * Metoda za grafičko renderiranje
     * @param sb SpriteBatch
     */
    public void render(SpriteBatch sb){
        states.peek().render(sb);
    }
}
