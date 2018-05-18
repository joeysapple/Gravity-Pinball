package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.matija.gravitydemo1.states.Assets;
import com.matija.gravitydemo1.states.PlayState;

/**
 * Created by Korisnik on 23.3.2018..
 */

/**
 * Klasa koja modelira raketu. Položaj, brzina i akceleracija sadržane su u varijablama position, velocity i acceleration.
 * Masa rakete se nalazi u varijabli mass, a grafički model je sadržan u varijabli rockeSprite. Granice rakete modelirane
 * su varijablom bounds tipa Circle.
 */
public class Rocket implements Drawable,Movable{
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;
    private double mass;

    private Sprite rocketSprite;
    private Circle bounds;
    private Vector2 oldAcc;
    private GraphicsModel model;

    private int score;
    /**
     * Pretpostavljeni konstruktor
     */
    public Rocket(){
        this(null,null,null,1);
    }

    /**
     * Konstruktor
     * @param position Pozicija rakete
     * @param velocity Brzina rakete
     * @param acceleration Akceleraija rakete
     * @param mass Masa rakete
     */
    public Rocket(Vector2 position, Vector2 velocity, Vector2 acceleration, double mass){
        this.position = new Vector2(position.x,position.y);
        this.velocity = new Vector2(velocity.x,velocity.y);
        this.acceleration = new Vector2(acceleration.x,acceleration.y);
        this.mass = mass;
        this.model = new SpriteGraphicsModel(Assets.rocket);
        model.sprite.setSize(40,40);
        bounds = new com.badlogic.gdx.math.Circle(position.x,position.y,20);

        /**
         * Ovo je inicijalizacija za potrebe Verletove metode integracije
         */
        Vector2 posDiff = new Vector2(PlayState.otherCirclePos.x,PlayState.otherCirclePos.y);
        posDiff.sub(this.position);
     //   System.out.println("u stvaranju razlika pozicije" + " "+ posDiff);
     //   System.out.println("u stvaranju razlika pozicije num2" + " "+ posDiff.len2());
     //   System.out.println("u stvaranju acc num" + " "+ ((float)PlayState.G*100/posDiff.len2()));
        // Vector2 acc = new Vector2(posDiff.x,posDiff.y);
        float magnitude = (float)PlayState.G*100/posDiff.len2();
        posDiff.nor();
        posDiff.scl(magnitude);
        oldAcc = new Vector2(posDiff.x,posDiff.y);
      //  System.out.println("u stvaranju acc" + " "+ posDiff);
     //   circle = new Sprite("rocket.png");

        //postavljanje skora
        score = 0;
    }

    @Override
    public void draw(SpriteBatch sb) {
     //   sb.draw(model.texture, position.x-20, position.y-20,40,40);
        model.sprite.setCenter(position.x,position.y);
        model.sprite.setOriginCenter();
        model.sprite.setRotation((float) ( Math.atan2(velocity.y,velocity.x)/Math.PI*180));
        model.sprite.draw(sb);

    }

    @Override
    public void dispose(){
        model.dispose();
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    @Override
    public Vector2 getVelocity() {
        return velocity;
    }

    @Override
    public Vector2 getAcceleration() {
        return acceleration;
    }

    public Sprite getRocketSprite() {
        return rocketSprite;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }
    public void setAcceleration(float x,float y) {
        this.acceleration.x = x;
        this.acceleration.y = y;
    }

    public void setTexture(Sprite t){
        this.rocketSprite = t;
    }

    public Circle getBounds() {
        return bounds;
    }

    public double getMass() {
        return mass;
    }

    public void setPosition(float x,float y) {
        this.position.x = x;
        this.position.y = y;
        this.bounds.setPosition(x,y);
    }

    public void setBounds(com.badlogic.gdx.math.Circle bounds) {
        this.bounds = bounds;
    }

    /**
     * Metoda za izačun novog položaja i brzine na temelju starih parametara i sile koja trenutno djeluje
     * @param dt Korak integracije, tj. vremenski interval koji je protekao od prethodnog poziva
     * @param planet Planet koji djeluje na raketu
     */
    public void update(float dt, Planet planet){

        Vector2 posDiff = new Vector2(this.position.x,this.position.y);
        posDiff.sub(planet.getPosition());

    //    System.out.println("Razlika pozicije " + posDiff);
        double posDiffLength2 = posDiff.len2();
    //    System.out.println("Razlika pozicije duljina " + posDiffLength2);
        posDiff.setLength((float) ((float) PlayState.G*planet.getMass()/posDiffLength2)).scl(-1);
    //    System.out.println("Sila " + posDiff);
        posDiff.add(this.acceleration);
        this.position.add(this.velocity.x*dt,this.velocity.y*dt)
                                        .add((float)0.5*dt*dt*posDiff.x,(float)0.5*dt*dt*posDiff.y);

        this.velocity.add(posDiff.x*dt,posDiff.y*dt);
      /*  position.add(dt*(velocity.x+dt*oldAcc.x/2),dt*(velocity.y+dt*oldAcc.y/2));
        System.out.println("Pozicija" + " "+position);
        Vector2 posDiff = new Vector2(planet.getPosition().x,planet.getPosition().y);
        posDiff.sub(this.position);
       // Vector2 acc = new Vector2(posDiff.x,posDiff.y);
        float magnitude = (float) (PlayState.G*planet.getMass()/posDiff.len2());
        posDiff.setLength(1);

        posDiff.setLength(magnitude);
        posDiff.add(this.acceleration);
        setAcceleration(0,0);
        velocity.add(dt*(oldAcc.x+posDiff.x)/2,dt*(oldAcc.y+posDiff.y)/2);
        oldAcc.set(posDiff.x,posDiff.y);
        System.out.println("duljuna framea" + " "+dt);*/
        bounds.setPosition(position.x,position.y);
        setAcceleration(0,0);

       /* position.add(velocity.x*dt,velocity.y*dt);
        System.out.println("Brzina glavna " + velocity);
        bounds.setPosition(position.x,position.y);*/


    }

    public void setScore(int score){
        this.score = score;
    }

    public int getScore(){
        return score;
    }

}
