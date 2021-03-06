package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.matija.gravitydemo1.GravityDemo1;
import com.matija.gravitydemo1.sprites.Rocket;

import static com.badlogic.gdx.Gdx.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;


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
    private Camera cam;




    private Stage stage;
    private SpriteBatch batch;
    private Viewport viewport;

    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin touchpadSkin;
    private Drawable touchBackground;
    private Drawable touchKnob;
/*

    private Label brzina;
    private Label brzinaIznos;
    private Label akceleracija;
    private Label akceleracijaIznos;
    private Label bodovi;
    private Label bodoviIznos;

*/

    private FreeTypeFontGenerator fontGen = new FreeTypeFontGenerator(Gdx.files.internal("Comic_sans.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

    private Text brzinaB;
    private Text brzinaIznosB;
    private Text akceleracijaB;
    private Text akceleracijaIznosB;
    private Text bodoviB;
    private Text bodoviIznosB;

    @Override
    public void create() {
        //Inicijalizacija varijabli s vrijednostima iz rakete

        acceleration = rocket.getAcceleration();
        mass = rocket.getMass();
        velocity = rocket.getVelocity();




        //Inicijalizacija touchpada

        touchpadSkin = new Skin();
        touchpadSkin.add("touchBackground", new Texture("touchpadBg.png"));
        touchpadSkin.add("touchKnob", new Texture("touchKnob.png"));

        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadSkin.getDrawable("touchBackground").setMinHeight(70);
        touchpadSkin.getDrawable("touchBackground").setMinWidth(70);
        touchBackground = touchpadSkin.getDrawable("touchBackground");
        touchpadSkin.getDrawable("touchKnob").setMinHeight(30);
        touchpadSkin.getDrawable("touchKnob").setMinWidth(30);
        touchKnob = touchpadSkin.getDrawable("touchKnob");

        touchpadStyle.background = touchBackground;
        touchpadStyle.knob = touchKnob;

        touchpad = new Touchpad(0, touchpadStyle);
        touchpad.setBounds(15, 15, 200, 200);

        //Labele FONTOVI
        //BitmapFont font = new BitmapFont();
/*

        Label.LabelStyle ls = new Label.LabelStyle(new BitmapFont(), Color.LIGHT_GRAY);
        brzina = new Label("Speed", ls);
        brzinaIznos = new Label("00.00", ls);
        akceleracija = new Label("Acceleration", ls);
        akceleracijaIznos = new Label("00.00", ls);

        bodovi = new Label("Score", ls);
        bodoviIznos = new Label("0", ls);
*/


        parameter.size=15;
        parameter.color= Color.WHITE;
        parameter.borderColor= Color.BLACK;
        parameter.borderWidth = 1;

        brzinaB = new Text("Speed" , fontGen, parameter, 10, 60);
        brzinaIznosB = new Text( "00.00", fontGen,parameter, 15, 40);
        akceleracijaB = new Text("Boost", fontGen, parameter, 170, 60);
        akceleracijaIznosB = new Text("00.00", fontGen, parameter, 175, 40);
        bodoviB = new Text("Score: ", fontGen, parameter, 5, 395);
        bodoviIznosB = new Text("0", fontGen, parameter, 10, 375);


        //Viewport
        viewport = new StretchViewport(GravityDemo1.WIDTH/2, GravityDemo1.HEIGHT/2,
                new OrthographicCamera());
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        Table table = new Table();
        Table lijeva = new Table();
        Table sredina = new Table();
        Table desna = new Table();
        Table gornja = new Table();

        table.setWidth(230);
        table.setHeight(400);

        gornja.top();
        gornja.right();
        gornja.add(bodoviB);
        gornja.row();
        gornja.add(bodoviIznosB);
        gornja.setWidth(230);
        gornja.setHeight(400);

        table.bottom();
        table.add(lijeva).expandX();
        table.add(sredina).expandX();
        table.add(desna).expandX();

        lijeva.bottom();
        lijeva.add(brzinaB).expandX();
        lijeva.row();
        lijeva.add(brzinaIznosB).expandX();

        desna.bottom();
        desna.right();
        desna.add(akceleracijaB).expandX();
        desna.row();
        desna.add(akceleracijaIznosB).expandX();

        sredina.center();
        sredina.add(touchpad);

        gornja.top();
        //table.left();
        //table.add(bodovi);
        //table.row();
        //table.add(bodoviIznos);



                //Dodavanje touchpada u stage, postavljanje input processora;
        stage.addActor(gornja);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);


        stage.act(Gdx.graphics.getDeltaTime());

        /*
        touchpad.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                    float deltaX = ((Touchpad) actor).getKnobPercentX();
                    float deltaY = ((Touchpad) actor).getKnobPercentY();
                    rocket.setAcceleration(deltaX * 2, deltaY * 2);
            }

        });*/
        //Listener za touchpad



    }

    public void update(float dt){
        if(touchpad.isTouched()){
            float deltaX = touchpad.getKnobPercentX();
            float deltaY = touchpad.getKnobPercentY();
            rocket.setAcceleration(deltaX * 2, deltaY * 2);
        }

        double iznosAkceleracije = Math.sqrt(
                Math.pow(rocket.getAcceleration().x, 2) +
                Math.pow(rocket.getAcceleration().y, 2));
        iznosAkceleracije = (double)Math.round(iznosAkceleracije * 100d)/100d;

        akceleracijaIznosB.setText(String.valueOf(iznosAkceleracije));

        double iznosBrzine = Math.sqrt(
                Math.pow(rocket.getVelocity().x, 2) +
                        Math.pow(rocket.getVelocity().y, 2));
        iznosBrzine = (double)Math.round(iznosBrzine * 100d)/100d;

        brzinaIznosB.setText(String.format("%3.2f",iznosBrzine));

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render() {



        batch.setProjectionMatrix(stage.getCamera().combined);
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



    public ControlBoard(Rocket rocket, SpriteBatch batch, Camera cam){

        this.rocket = rocket;
        this.batch = batch;

    }


}



