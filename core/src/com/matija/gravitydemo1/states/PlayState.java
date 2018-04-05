package com.matija.gravitydemo1.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.matija.gravitydemo1.GravityDemo1;
import com.matija.gravitydemo1.sprites.Rocket;
import com.matija.gravitydemo1.sprites.Planet;
import com.matija.gravitydemo1.sprites.PositionSimulator;

/**
 * Created by Korisnik on 23.3.2018..
 */

/**
 * Klasa koja predstavlja stanje igranja. Nasljeđuje klasu State.
 */
public class PlayState extends State {
    private Rocket rocket;
    private Planet planet;

    private Sprite backroundSprite;
    private Music music;

    public final static Vector2 otherCirclePos = new Vector2(GravityDemo1.WIDTH/4,250);
    public final static double G = 1000.0f;

    private PositionSimulator ps;

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
        rocket = new Rocket(new Vector2(GravityDemo1.WIDTH/4,50),new Vector2((float) 1,0),new Vector2(0,0),1);
        planet = new Planet(new Vector2(GravityDemo1.WIDTH/4,250),new Vector2(0,0),new Vector2(0,0),100);

        /*
        Inicijalizacija pozadine
         */
        Texture backround = new Texture(Gdx.files.internal("space.jpg"));
        backround.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        backroundSprite = new Sprite(backround);

        ps = new PositionSimulator(rocket,planet);

        /*
        Inicijalizacija glazbe
         */
        music = Gdx.audio.newMusic(Gdx.files.internal("rocketSound.mp3"));
        music.setLooping(true);
        music.setVolume(0.90f);
        music.play();
    }


    @Override
    /**
     * Metoda za upravljanje inputom. Ukoliko korisnik dotakne ekran, raketi se dodaje akceleracija udesno
     */
    public void handleInput() {
        if (Gdx.input.isTouched()){
            rocket.setAcceleration(4,0);
            System.out.println("pritisak");
        }
    }

    /**
     *
     * @param dt Vrijeme proteklo od zadnjeg ažuriranja
     */
    @Override
    public void update(float dt) {
        handleInput();  //provjerava se input

        float x = rocket.getPosition().x;
        float y = rocket.getPosition().y;   //dohvaća se trenutna pozicija rakete

        rocket.update(dt,planet);   //ažuriraju se parametri rakete
        ps.simulate(dt);    //simulacija putanje na temelju trenutnih parametara

        cam.position.x = cam.position.x + (rocket.getPosition().x - x);
        cam.position.y = cam.position.y + (rocket.getPosition().y - y); //pomicanje kamere
        cam.update(); //ažuriranje kamere


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();

        backroundSprite.draw(sb); //iscrtanjanje pozadine

        /*
        Iscrtavanje rakete. Prvi parametar je grafički model rakete (slika rakete)
                            Drugi i treći parametri su koordinate donjeg lijevog kuta slike. Budući da je pozicija
                            rakete u središtu rakete, da bi se dobio donji lijevi kut, potrebno je od središta oduzeti
                            polovicu duljine i širine slike.
         */
        sb.draw(rocket.getRocketSprite(), rocket.getPosition().x-20, rocket.getPosition().y-20,40,40);

        /*
        Iscrtavanje planeta
         */
        sb.draw(planet.getPlanetSprite(),planet.getPosition().x-planet.getRadius(), planet.getPosition().y-planet.getRadius(),150,150);

        /*
        Provjera je li došlo do sudara. Ukoliko jest, igra završava
         */
        if (planet.getBounds().overlaps(rocket.getBounds())){
            System.out.println("preklapanje");
            gsm.set(new PlayState(gsm));
        }

        /*
        Iscrtavanje putanje
         */
        for (Rocket r:ps.getRockets()){
            sb.draw(r.getRocketSprite(),r.getPosition().x-2, r.getPosition().y-2,5,5);
            if (planet.getBounds().overlaps(r.getBounds())) break;
           // System.out.println("hah" + r.getPosition().x + " "+r.getPosition().y);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        /*rocket.getCircle().dispose()*/;
    }
}
