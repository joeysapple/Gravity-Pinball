package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.matija.gravitydemo1.GravityDemo1;
import com.matija.gravitydemo1.sprites.Moon;
import com.matija.gravitydemo1.sprites.MovementSimulator;
import com.matija.gravitydemo1.sprites.Point;
import com.matija.gravitydemo1.sprites.PositionSimulator1;
import com.matija.gravitydemo1.sprites.Rocket;
import com.matija.gravitydemo1.sprites.Planet;
import com.matija.gravitydemo1.sprites.ControlBoard;

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
    private Music music;
    public boolean potisak = true;
    public final static Vector2 otherCirclePos = new Vector2(GravityDemo1.WIDTH/4,250);
    public final static double G = 1000.0f;
    boolean noviKvadrat = false;
   // private PositionSimulator ps;
    private MovementSimulator ms;
    private PositionSimulator1 ps;

    private Label debug;

    private ControlBoard controlBoard;
    /**
     * Konstruktor
     * @param gsm gsm
     */
    public PlayState(GameStateManager gsm) {
        super(gsm);
        /*
        Inicijalizacija kamere
         */
        cam.setToOrtho(false, GravityDemo1.WIDTH/2,GravityDemo1.HEIGHT/2);
        cam.position.y = cam.position.y-100;
        /*
        Instanciranje rakete i planeta
         */
        rocket = new Rocket(new Vector2(GravityDemo1.WIDTH/4,50),new Vector2((float) 20,0),new Vector2(0,0),1);
        planet = new Planet(new Vector2(GravityDemo1.WIDTH/4,250),new Vector2(0,0),new Vector2(0,0),100,170);
        moon = new Moon(new Vector2(400,400),new Vector2(-10,-17),new Vector2(0,0),1);
        //za beskonacnu verziju: boolean ascOrDesc = true;
        brojacPlaneta = 1;
        coordx = 2;
        planetCreationInProgress = false;

        /*
        Inicijalizacija pozadine
         */
        Texture backround = new Texture(Gdx.files.internal("space.jpg"));
        backround.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backroundSprite = new Sprite(backround);

     //   ps = new PositionSimulator(rocket,planet);
        ps = new PositionSimulator1();

        ps.setPlanet(planet);
        ps.setRocket(rocket);

        ms = new MovementSimulator();

        /*
        Inicijalizacija glazbe
         */
        music = Gdx.audio.newMusic(Gdx.files.internal("rocketSound.mp3"));
        music.setLooping(true);
        music.setVolume(0.90f);
        music.play();

        /*
        Inicijalizacija kontrolne ploce
         */


        controlBoard = new ControlBoard(rocket, new SpriteBatch(), cam);
        controlBoard.create();

        Label.LabelStyle ls = new Label.LabelStyle(new BitmapFont(), Color.FIREBRICK);
        debug = new Label("      ", ls);

    }


    @Override
    /**
     * Metoda za upravljanje inputom. Ukoliko korisnik dotakne ekran, raketi se dodaje akceleracija udesno
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

        float x = rocket.getPosition().x;
        float y = rocket.getPosition().y;   //dohvaća se trenutna pozicija rakete



    //    rocket.update(dt,planet);   //ažuriraju se parametri rakete
        ms.move(rocket, planet,dt);
        ms.move(moon, planet,dt);

        rocket.getBounds().setPosition(rocket.getPosition().x,rocket.getPosition().y);
        ps.simulate(dt);
        rocket.setAcceleration(0,0);

            //simulacija putanje na temelju trenutnih parametara
        potisak = false;
        noviKvadrat = false;
        int j=0;
        for (int i=0;i<10000;i++){
            j++;
        }

        cam.position.x = cam.position.x + (rocket.getPosition().x - x);
        cam.position.y = cam.position.y + (rocket.getPosition().y - y); //pomicanje kamere
        cam.update(); //ažuriranje kamere

        //Azuriranje kontrolne ploce
        controlBoard.update(dt);
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();



      //  cam.zoom = 2.0f;
        backroundSprite.setCenter(cam.position.x,cam.position.y);
        backroundSprite.draw(sb); //iscrtanjanje pozadine

        /*
        Iscrtavanje rakete. Prvi parametar je grafički model rakete (slika rakete)
                            Drugi i treći parametri su koordinate donjeg lijevog kuta slike. Budući da je pozicija
                            rakete u središtu rakete, da bi se dobio donji lijevi kut, potrebno je od središta oduzeti
                            polovicu duljine i širine slike.
         */


       rocket.draw(sb);
       moon.draw(sb);
        /*
        Iscrtavanje planeta
         */
        planet.draw(sb);

        /*
        Provjera je li došlo do sudara. Ukoliko jest, igra završava
         */
        if (planet.getBounds().overlaps(rocket.getBounds())){
           // System.out.println("preklapanje");
            dispose();
            gsm.set(new GameOverState(gsm));
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
        if (rocket.getPosition().y > planet.getPosition().y+ planet.getRadius()*1.2) {


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
                    gsm.set(new GameOverState(gsm));
                }

                int rad = 60 + brojacPlaneta * 10;
                /*planetT = new Planet(new Vector2(GravityDemo1.WIDTH/4,450),new Vector2(0,0),new Vector2(0,0),100,170);
                moonT = new Moon(new Vector2(400,400),new Vector2(-10,-17),new Vector2(0,0),1);*/
                //planet.dispose();
                //moon.dispose();
                //planet = new Planet(new Vector2(GravityDemo1.WIDTH * coordx / 4, rocket.getPosition().y + 150), new Vector2(0, 0), new Vector2(0, 0), rad, rad);
                //ps.setPlanet(planet);

                planet.setPosition(new Vector2(GravityDemo1.WIDTH * coordx / 4, rocket.getPosition().y + 150));
                planet.setRadius(rad);
                planet.setBounds();
                planet.setMass(rad);

                ps.setInitialized(false);

                System.out.println("brojacPlaneta: "+brojacPlaneta);
                System.out.println("coordx: "+coordx);
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

                if (brojacPlaneta%3==0) {
                    moon = new Moon(new Vector2(planet.getPosition().x+150, planet.getPosition().y+50), new Vector2(-10, -17), new Vector2(0,0), 1);
                }

                planetCreationInProgress = false;
            }
        }

        /*
        Iscrtavanje putanje
         */

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





        //Inace ne iscrta putanju, nema nikakvog smisla

        debug.draw(sb, 0);
        /*
        Iscrtavanje kontrolne ploce --Marin
         */


        controlBoard.render();

        sb.end();
    }

    @Override
    public void dispose() {
       rocket.dispose();
       planet.dispose();
       moon.dispose();
       music.stop();
       music.dispose();
       ps.dispose();
       Assets.newManager();
    }
}
