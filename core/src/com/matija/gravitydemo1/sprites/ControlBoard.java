package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.matija.gravitydemo1.GravityDemo1;
import com.matija.gravitydemo1.sprites.Rocket;

import static com.badlogic.gdx.Gdx.*;



/*
TODO:
DONJI DIO:
    akceleracija

 */

//Klasa kontrolne ploce s joystickom i pokazivacem brzine i akceleracije

public class ControlBoard implements ApplicationListener{

    private Rocket rocket;
    private Vector2 velocity;
    private double mass;
    private Vector2 acceleration;



    private Stage stage;
    private SpriteBatch batch;
    private Viewport viewport;

    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;


    @Override
    public void create() {
        //Inicijalizacija varijabli s vrijednostima iz rakete

        acceleration = rocket.getAcceleration();
        mass = rocket.getMass();
        velocity = rocket.getVelocity();




        //Inicijalizacija touchpada

        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("touchpadKnob.png"));
        touchpadSkin.add("touchKnob", new Texture("touchKnob.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchKnob = touchpadSkin.getDrawable("touchKnob");

        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;

        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(25, 25, 200, 200);



        //Viewport
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
                new OrthographicCamera());

        stage = new Stage(viewport, batch);


        //Dodavanje touchpada u stage, postavljanje input processora;
        stage.addActor(touchpad);
        Gdx.input.setInputProcessor(stage);




        //Listener za touchpad
        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                float deltaX = ((Touchpad) actor).getKnobPercentX();
                float deltaY = ((Touchpad) actor).getKnobPercentY();
                rocket.setAcceleration(deltaX*10, deltaY*10);

            }
        });


    }

    @Override
    public void dispose() {
    }

    @Override
    public void render() {

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

    }

    @Override
    public void resume() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resize(int width, int height) {
    }



    public ControlBoard(Rocket rocket, SpriteBatch batch){

        this.rocket = rocket;
        this.batch = batch;

    }


}



