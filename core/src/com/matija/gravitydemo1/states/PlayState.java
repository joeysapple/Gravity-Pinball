package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.matija.gravitydemo1.GravityDemo1;
import com.matija.gravitydemo1.sprites.Moon;
import com.matija.gravitydemo1.sprites.MovementSimulator;
import com.matija.gravitydemo1.sprites.Point;
import com.matija.gravitydemo1.sprites.PositionSimulator1;
import com.matija.gravitydemo1.sprites.Rocket;
import com.matija.gravitydemo1.sprites.Planet;
import com.matija.gravitydemo1.sprites.ControlBoard;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Korisnik on 23.3.2018..
 */

/**
 * Klasa koja predstavlja stanje igranja. Nasljeđuje klasu State.
 */
public class PlayState extends State {
    private Rocket rocket;
    private Planet planet;
    private Moon moon;
    // za beskonacnu verziju: private boolean ascOrDesc;    //true znaci asc, false znaci desc
    // za beskonacnu verziju: private boolean zen;      //true znaci beskonacna verzija, false znaci verzija s krajem
    private int brojacPlaneta;
    private int coordx;
    private boolean planetCreationInProgress;
    private int pom;

    private Sprite backroundSprite;
    private Sprite redBackgroundSpriteLeft;
    private Sprite redBackgroundSpriteRight;
    private Sprite redBackgroundSpriteDown;

    private Music music;
    private Sound crashingSound;
    private Sound scoreUpSound;
    private Sound successSound;
    private Sound timerTickSound;
    private Sound timerGoSound;

    public boolean potisak = true;
    public final static Vector2 otherCirclePos = new Vector2(GravityDemo1.WIDTH/4,250);
    public final static double G = 1000.0f;
    boolean noviKvadrat = false;
   // private PositionSimulator ps;
    private MovementSimulator ms;
    private PositionSimulator1 ps;

    private Label debug;

    private ControlBoard controlBoard;
    private int downLimit = -GravityDemo1.HEIGHT/2-150;
    private int leftLimitOffset =- 100;
    private int rigthLimitOffset = 0;

    //za counter
 /*   private boolean initState;
    private int timeCounter;
    private int currentMil;*/

    private boolean initState = true;

    private double currentMil;
    private double timeCounter;
    private int currentSec;

    private Map<Integer,Sprite> timerDigits;

    /**
     * Konstruktor
     * @param gsm gsm
     */
    public PlayState(GameStateManager gsm) {
        super(gsm);
        /*
        Inicijalizacija kamere
         */
        //cam.position.set(cam.viewportWidth/2,cam.viewportHeight/2,0);
        cam.setToOrtho(false, GravityDemo1.WIDTH,GravityDemo1.HEIGHT);



        //cam.position.y = cam.position.y-GravityDemo1.WIDTH/2;
        cam.position.y=cam.position.y-300;
        /*
        Instanciranje rakete i planeta
         */
        rocket = new Rocket(new Vector2(GravityDemo1.WIDTH/2,0),new Vector2(0,0),new Vector2(0,0),1);

        //inicijalna rotacija rakete

        planet = new Planet(new Vector2(GravityDemo1.WIDTH/2,250),new Vector2(0,0),new Vector2(0,0),60,60);
        moon = new Moon(new Vector2(400,400),new Vector2(-10,-17),new Vector2(0,0),1);
        moon.reposition(planet);
        //za beskonacnu verziju: boolean ascOrDesc = true;
        brojacPlaneta = 1;
        coordx = 2;
        planetCreationInProgress = false;
        initState = true;

        /*
        Inicijalizacija pozadine
         */
        Texture background = Assets.manager.get(Assets.background,Texture.class);
        background.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backroundSprite = new Sprite(background);
        backroundSprite.setSize(GravityDemo1.WIDTH,GravityDemo1.HEIGHT);

        Texture redBackground = Assets.manager.get(Assets.redBackground,Texture.class);
        redBackground.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        //lijeva granica
        redBackgroundSpriteLeft = new Sprite(redBackground);
        redBackgroundSpriteLeft.setSize(GravityDemo1.WIDTH+leftLimitOffset,GravityDemo1.HEIGHT);
        redBackgroundSpriteLeft.setCenter(-GravityDemo1.WIDTH/2-leftLimitOffset/2,cam.position.y);

        //desna granica
        redBackgroundSpriteRight = new Sprite(redBackground);
        redBackgroundSpriteRight.setSize(GravityDemo1.WIDTH+rigthLimitOffset,GravityDemo1.HEIGHT);
        redBackgroundSpriteRight.setCenter(GravityDemo1.WIDTH + GravityDemo1.WIDTH/2,cam.position.y);

        //donja granica
        redBackgroundSpriteDown = new Sprite(redBackground);
        redBackgroundSpriteDown.setSize(GravityDemo1.WIDTH+rigthLimitOffset-leftLimitOffset,GravityDemo1.HEIGHT);
        redBackgroundSpriteDown.setCenter(GravityDemo1.WIDTH/2 + GravityDemo1.WIDTH/2-rigthLimitOffset+leftLimitOffset,downLimit);

     //   ps = new PositionSimulator(rocket,planet);
        ps = new PositionSimulator1();

        ps.setPlanet(planet);
        ps.setRocket(rocket);

        ms = new MovementSimulator();

        /*
        Inicijalizacija glazbe
         */
       // music = Gdx.audio.newMusic(Gdx.files.internal("rocketSound.mp3"));
        music = Assets.manager.get(Assets.sound,Music.class);

        music.setLooping(true);
        music.setVolume(1.0f);
        music.play();

        crashingSound = Assets.manager.get(Assets.crash,Sound.class);
        scoreUpSound = Assets.manager.get(Assets.scoreUp,Sound.class);
        successSound = Assets.manager.get(Assets.success,Sound.class);
        timerTickSound = Assets.manager.get(Assets.timerTick,Sound.class);
        timerGoSound = Assets.manager.get(Assets.timerGo,Sound.class);
        /*
        Inicijalizacija kontrolne ploce
         */


        controlBoard = new ControlBoard(rocket, new SpriteBatch(), cam);
        controlBoard.create();

        Label.LabelStyle ls = new Label.LabelStyle(new BitmapFont(), Color.FIREBRICK);
        debug = new Label("      ", ls);

      /*  font = new BitmapFont();
        font.setColor(Color.GOLD);
        font.getData().setScale(2.8f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
       */

        currentMil = System.currentTimeMillis();
        timeCounter = 0;
        currentSec = 4;
        timerDigitsInit();

      //  ms.move(rocket,planet,0.01f);
        rocket.getVelocity().y=0.000001f;
    }


    @Override
    /**
     * Metoda za upravljanje inputom
     */
    public void handleInput() {
        //Stari input, novi je u klasi ControlBoard
        /*
        if (Gdx.input.isTouched()){

            if (Gdx.input.getX()<Gdx.graphics.getWidth()/2)
                rocket.setAcceleration(-4,0);
            else
                rocket.setAcceleration(4,0);
           // System.out.println("pritisak");
            potisak = true;
           // System.out.println("x koordinata dodira "+ Gdx.input.getX() + "x koordinata rakete "+ rocket.getPosition().x + Gdx.graphics.getWidth());
        }
        */
    }

    /**
     *
     * @param dt Vrijeme proteklo od zadnjeg ažuriranja
     */
    @Override
    public void update(float dt) {
        //handleInput();  //provjerava se input

       if (initState) {
            initState();
        }

        float x = rocket.getPosition().x;
        float y = rocket.getPosition().y;   //dohvaća se trenutna pozicija rakete



    //    rocket.update(dt,planet);   //ažuriraju se parametri rakete

        if (!initState) {
            ms.move(rocket, planet, dt);
            ms.move(moon, planet, dt);
        }

        rocket.getBounds().setPosition(rocket.getPosition().x,rocket.getPosition().y);
        moon.setBounds();
        ps.simulate(dt);
        rocket.setAcceleration(0,0);

            //simulacija putanje na temelju trenutnih parametara
        potisak = false;
        noviKvadrat = false;

        cam.position.x = cam.position.x + (rocket.getPosition().x - x);
        cam.position.y = cam.position.y + (rocket.getPosition().y - y); //pomicanje kamere*/

        cam.update(); //ažuriranje kamere

        //Azuriranje kontrolne ploce
        controlBoard.update(dt);
        this.extraPoints();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();



     //   cam.zoom = 2.0f;
        backroundSprite.setCenter(cam.position.x,cam.position.y);
        backroundSprite.draw(sb); //iscrtanjanje pozadine

        redBackgroundSpriteLeft.setCenter(-GravityDemo1.WIDTH/2+leftLimitOffset/2,cam.position.y);
        redBackgroundSpriteLeft.draw(sb); //iscrtanjanje pozadine

        redBackgroundSpriteRight.setCenter(GravityDemo1.WIDTH + GravityDemo1.WIDTH/2+rigthLimitOffset,cam.position.y);
        redBackgroundSpriteRight.draw(sb); //iscrtanjanje pozadine

        redBackgroundSpriteDown.setCenter(GravityDemo1.WIDTH/2+(rigthLimitOffset+leftLimitOffset)/2 ,downLimit);
        redBackgroundSpriteDown.draw(sb);

        /*
        Iscrtavanje rakete. Prvi parametar je grafički model rakete (slika rakete)
                            Drugi i treći parametri su koordinate donjeg lijevog kuta slike. Budući da je pozicija
                            rakete u središtu rakete, da bi se dobio donji lijevi kut, potrebno je od središta oduzeti
                            polovicu duljine i širine slike.
         */


       rocket.draw(sb);

       //iscrtavanje putanje
        if (ps.getRockets().get(0).getBounds().overlaps(rocket.getBounds())){
            //  noviKvadrat = true;
        }
        int index=0;

        for (index=0;index<=ps.getIndex();index++){
            Point r = ps.getRockets().get(index);
            r.draw(sb);

            if (planet.getBounds().overlaps(r.getBounds())) break;
            // System.out.println("hah" + r.getPosition().x + " "+r.getPosition().y);

        }

       moon.draw(sb);
        /*
        Iscrtavanje planeta
         */
        planet.draw(sb);


        if (initState && currentSec<4 && currentSec>0){


            timerDigits.get(currentSec).setCenter(GravityDemo1.WIDTH/2,GravityDemo1.HEIGHT/2);
            timerDigits.get(currentSec).draw(sb);

        }

        /*


        Provjera je li došlo do sudara. Ukoliko jest, igra završava
         */
        if (planet.getBounds().overlaps(rocket.getBounds()) || moon.getBounds().overlaps(rocket.getBounds())){
           // System.out.println("preklapanje");
            crashingSound.play();
            dispose();
            gsm.set(new GameOverState(gsm,rocket.getPoints(), false));
        }

        //raketa je izvan granice
        if (rocket.getPosition().x<0+leftLimitOffset || rocket.getPosition().x>GravityDemo1.WIDTH+rigthLimitOffset || rocket.getPosition().y<downLimit+GravityDemo1.HEIGHT/2){
            crashingSound.play();
            dispose();
            gsm.set(new GameOverState(gsm,rocket.getPoints(), false));
        }


        //printanje samo svaki deseti put
        if (pom==10) {
            float posTX = planet.getPosition().x;
            float posTY = planet.getPosition().y;
            System.out.print(posTX);
            System.out.print(", ");
            System.out.println(posTY);
            float posRX = rocket.getPosition().x;
            float posRY = rocket.getPosition().y;
            System.out.print(posRX);
            System.out.print(", ");
            System.out.println(posRY);
            pom = 0;
        } else {
            pom++;
        }

        /*Provjera je li planet "ispod" rakete*/
        /*float deltaY = rocket.getPosition().y - planet.getPosition().y;
        float deltaX = rocket.getPosition().x - planet.getPosition().x;
        double distance =Math.sqrt(deltaY*deltaY + deltaX*deltaX);
        if (distance > planet.getRadius()*2) {*/
        if (rocket.getPosition().y > planet.getPosition().y+ planet.getRadius()+GravityDemo1.HEIGHT/2) {


            System.out.println("TU STVARAMO NOVI PLANET");


            if (planetCreationInProgress) {}
            else {
                planetCreationInProgress = true;
                //ako beskonacna verzija:
                /*if (ascOrDesc) {
                    brojacPlaneta++;
                    if (brojacPlaneta==10) {ascOrDesc = false;}
                } else {
                    brojacPlaneta--;
                    if (brojacPlaneta==1) {ascOrDesc = true;}
                }*/
                //ako verzija s krajem:
                brojacPlaneta++;
                if (brojacPlaneta == 10) {
                    /*if (zen) { brojacPlaneta = 0;}    //ako zen verzija, brojac se samo resetira
                        else { WORMHOLE } (zasad placeholder zaustavi igru)
                    */
                    dispose();
                    successSound.play(0.5f);
                    gsm.set(new GameOverState(gsm,rocket.getPoints(), true));
                }

                int rad = 60 + brojacPlaneta * 10;
                /*planetT = new Planet(new Vector2(GravityDemo1.WIDTH/4,450),new Vector2(0,0),new Vector2(0,0),100,170);
                moonT = new Moon(new Vector2(400,400),new Vector2(-10,-17),new Vector2(0,0),1);*/
                //planet.dispose();
                //moon.dispose();
                //planet = new Planet(new Vector2(GravityDemo1.WIDTH * coordx / 4, rocket.getPosition().y + 150), new Vector2(0, 0), new Vector2(0, 0), rad, rad);
                //ps.setPlanet(planet);

                planet.setPosition(new Vector2(GravityDemo1.WIDTH * coordx / 4, rocket.getPosition().y + 500+rad));
                planet.setRadius(rad);
                planet.setBounds();
                planet.setMass(rad);
                //planet.setOneOfModel(0);
                planet.setNextModel();

                planet.setCanGetPoints(true);
                rocket.increasePoints(5);

                scoreUpSound.play(0.6f);
                Gdx.input.vibrate(500);
                ps.setInitialized(false);
                moon.reposition(planet);
                downLimit = (int) (rocket.getPosition().y - (GravityDemo1.HEIGHT/2+150));


                System.out.println("brojacPlaneta: "+brojacPlaneta);
                System.out.println("coordx: "+coordx);
                System.out.println("moon :"+ moon.getPosition().x + " "+moon.getPosition().y);
                switch (coordx) {
                    case 1: coordx = 2; break;
                    case 2: {
                        if ( brojacPlaneta==3 || brojacPlaneta==7) {
                            coordx = 3;
                        } else {
                            coordx = 1;
                        }
                        break;
                    }
                    case 3: coordx = 2; break;
                }
                System.out.println("STVOREN NOVI PLANET");
                System.out.print("Y POZICIJA NOVOG PLANETA: ");
                System.out.println(planet.getPosition().y);
                System.out.print("X POZICIJA NOVOG PLANETA: ");
                System.out.println(planet.getPosition().x);

              /*  if (brojacPlaneta%3==0) {
                    moon = new Moon(new Vector2(planet.getPosition().x+150, planet.getPosition().y+50), new Vector2(-10, -17), new Vector2(0,0), 1);
                }*/

                planetCreationInProgress = false;
            }
        }

        /*
        Iscrtavanje putanje
         */







        //Inace ne iscrta putanju, nema nikakvog smisla

        debug.draw(sb, 0);
        /*
        Iscrtavanje kontrolne ploce --Marin
         */




        controlBoard.render();

      /*  if (initState){
            font.draw(sb,currentSec+"",GravityDemo1.WIDTH/2,GravityDemo1.HEIGHT/2);
        }*/

        sb.end();
    }

    private void extraPoints(){
        if (Intersector.intersectSegmentCircle(planet.getPosition(),moon.getPosition(),new Vector2(rocket.getBounds().x,rocket.getBounds().y),rocket.getBounds().radius)){
            if (planet.canGetPoints()) {
                scoreUpSound.play(0.6f);
                Gdx.input.vibrate(500);
                rocket.increasePoints(20);
                planet.setCanGetPoints(false);
            }
        }
    }
    @Override
    public void dispose() {
       rocket.dispose();
       planet.dispose();
       moon.dispose();
       music.stop();
      // music.dispose();
       ps.dispose();
       controlBoard.dispose();
      // Assets.newManager();
    }

    private void initState(){
        double currentM = System.currentTimeMillis();
        timeCounter+=(currentM - currentMil);
        currentMil = currentM;

        if (timeCounter>1000){
            timeCounter=0;
            System.out.println(currentSec);
            currentSec--;
            if (currentSec==0){
                initState=false;
                timerGoSound.play();
            }
            else if (timeCounter<4){
                timerTickSound.play();

            }
        }

    }

    private void timerDigitsInit(){

        timerDigits = new HashMap<Integer, Sprite>();

        //3
        Texture digit3 = Assets.manager.get(Assets.digit3,Texture.class);
        digit3.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Sprite sprite3 = new Sprite(digit3);
      //  sprite3.scale(0.5f);
        sprite3.setCenter(GravityDemo1.WIDTH/2,GravityDemo1.HEIGHT/2);

        timerDigits.put(3,sprite3);

        //2
        Texture digit2 = Assets.manager.get(Assets.digit2,Texture.class);
        digit2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Sprite sprite2 = new Sprite(digit2);
     //   sprite2.scale(0.5f);
        sprite2.setCenter(GravityDemo1.WIDTH/2,GravityDemo1.HEIGHT/2);

        timerDigits.put(2,sprite2);

        //1
        Texture digit1 = Assets.manager.get(Assets.digit1,Texture.class);
        digit2.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Sprite sprite1 = new Sprite(digit1);

        sprite1.setCenter(GravityDemo1.WIDTH/2,GravityDemo1.HEIGHT/2);

        timerDigits.put(1,sprite1);

    }

}
