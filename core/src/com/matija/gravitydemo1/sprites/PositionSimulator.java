package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.matija.gravitydemo1.states.PlayState;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Korisnik on 31.3.2018..
 */

/**
 * Klasa koja služi da bi se predvidjela putanja rakete na temelju trenutnih parametara
 */
public class PositionSimulator {
    private Rocket rocket;
    private Planet planet;
    LinkedList<Rocket> rockets;
  //  public LinkedList<Rocket> mockedRockets;
    Vector2 position;
    Vector2 velocity;
    Vector2 positionNorm;
    Vector2 thetaNorm;
    double planetMass;
    double rocketMass;
    double rocketDistance;
    private double theta;
    private double theta0;
    private double thetaPom;
    private double epsilon;
    private double r0;
    private double increment;
    private Vector2 vec1;
    /**
     * Konstruktor
     * @param rocket Raketa za koju se vrši predikcija putanje
     * @param planet Planet čija gravitaija djeluje na raketu
     */
    public PositionSimulator(Rocket rocket, Planet planet){
        this.rocket = rocket;
        rockets = new LinkedList<Rocket>();
     //   mockedRockets = new LinkedList<Rocket>();
        position = new Vector2();
        velocity = new Vector2();
        positionNorm = new Vector2();
        thetaNorm = new Vector2();
        planetMass = planet.getMass();
        rocketMass = rocket.getMass();
        vec1 = new Vector2();
        this.planet = planet;

        for (double theta = 0;theta <= Math.PI/2;theta+=0.1 ){

            rockets.add(new Rocket(new Vector2(0,0), new Vector2(0,0), new Vector2(0,0),1));
            Texture texture = new Texture(Gdx.files.internal("sphere.png"));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            rockets.peekLast().setTexture(new Sprite(texture));
            rockets.peekLast().setBounds(new com.badlogic.gdx.math.Circle(0,0,2 ));

        /*    mockedRockets.add(new Rocket(new Vector2(0,0), new Vector2(0,0), new Vector2(0,0),1));
            Texture texture1 = new Texture(Gdx.files.internal("dotM.jpg"));
            texture1.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            mockedRockets.peekLast().setTexture(new Sprite(texture1));
            mockedRockets.peekLast().setBounds(new com.badlogic.gdx.math.Circle(0,0,2 ));*/
        }
    }


    public void simulate(float dt, boolean simulate){
        Rocket r = rockets.get(0);
        double newRocketDistance = (Vector2.dst(r.getPosition().x,r.getPosition().y,rocket.getPosition().x,rocket.getPosition().y));


        if (r.getBounds().overlaps(rocket.getBounds())){
            this.move();
            //System.out.println("trebalo bi se pomaknuti");
            return;
        }

        if (newRocketDistance>rocketDistance){
            newTrajectory(dt);
            return;
        }
        else{
            rocketDistance = newRocketDistance;
        }

       if (simulate) this.newTrajectory(dt);
    }
    /**
     * Metoda koja služi za predviđanje putanje. Koristi se Keplerova jednadžba
     * @param dt
     */

    private void newTrajectory(float dt){
        position.set(rocket.getPosition().x - planet.getPosition().x,rocket.getPosition().y - planet.getPosition().y);
        velocity.set(rocket.getVelocity().x,rocket.getVelocity().y);
      //  System.out.println("Brzina" + " " + velocity);
        theta0 = Math.atan2(position.y,position.x);

    //    System.out.println("Theta je " + + theta0);
      //  System.out.println("Pozicija  " + + position.x + " "+position.y);

     //   System.out.println("Pozicija planeta " + + planet.getPosition().x + " "+planet.getPosition().y);
     //   System.out.println("Pozicija RAKETE  " + + rocket.getPosition().x + " "+rocket.getPosition().y);

        positionNorm.set(position.x,position.y);
        positionNorm.setLength(1);
     //   System.out.println("Pozicija NORMA  " + + positionNorm.x + " "+positionNorm.y);

        thetaNorm.set(positionNorm.x,positionNorm.y);
        thetaNorm.rotate90(1);
      //  System.out.println("THETA NORMA  " + + thetaNorm.x + " "+thetaNorm.y);
        double positionDistance = position.len();
        double velocityLength = velocity.len();

     //   System.out.println("Udaljenost  " + positionDistance);
        double angleVelocity = thetaNorm.dot(velocity)/positionDistance;
    //    System.out.println("kutna brzina" + angleVelocity);
        double radialVelocity = positionNorm.dot(velocity);

        double mi = rocketMass*planetMass/(rocketMass+planetMass);
        double L = mi * positionDistance*positionDistance*angleVelocity;
      //  System.out.println("h" + h);
        double E = 0.5*mi*(velocityLength*velocityLength) - PlayState.G*planetMass*rocketMass/positionDistance;
        epsilon = Math.sqrt(1+(2*E*L*L)/(mi*(PlayState.G*planetMass*rocketMass)*(PlayState.G*planetMass*rocketMass)));
        r0 = L*L/(mi*PlayState.G*planetMass*rocketMass);

       // System.out.println("A" + A);

      //  System.out.println(e);

        double v0arg = Math.atan2(velocity.y,velocity.x);
        thetaPom = Math.acos((1-(r0/positionDistance))/epsilon);

       /* System.out.println("r unit" + " "+ positionNorm);
        System.out.println("theta unit" + " "+ thetaNorm);
        System.out.println("w0" + " "+ angleVelocity);
        System.out.println("theta 0" + " "+ theta0);
        System.out.println("v0 kut" + " "+ v0arg);
        System.out.println("theta pomak" + " "+ thetaPom);
        System.out.println("epsilon " + epsilon);
        System.out.println("E "+ E );
        System.out.println("L "+ L );*/

        increment = 0.1;
        if (angleVelocity<0) increment=-0.1;
        theta = theta0+increment;


        double r = r0/(1-epsilon*Math.cos(theta+increment/40-theta0-thetaPom));

        float x = (float) ((float) r*Math.cos(theta+increment/40)) + planet.getPosition().x;
        float y = (float) ((float) r*Math.sin(theta+increment/40)) + planet.getPosition().y;

        double dis1 = Vector2.dst(rocket.getPosition().x,rocket.getPosition().y,x,y);
        vec1.set(x-rocket.getPosition().x,y-rocket.getPosition().y);
        double angle1 = Math.abs(Math.atan2(vec1.y,vec1.x)-Math.atan2(rocket.getVelocity().y,rocket.getVelocity().x));
        r = r0/(1-epsilon*Math.cos(theta+increment/40-theta0+thetaPom));
        x = (float) ((float) r*Math.cos(theta+increment/40)) + planet.getPosition().x;
        y = (float) ((float) r*Math.sin(theta+increment/40)) + planet.getPosition().y;

        double dis2 = Vector2.dst(rocket.getPosition().x,rocket.getPosition().y,x,y);
        vec1.set(x-rocket.getPosition().x,y-rocket.getPosition().y);
        double angle2 = Math.abs(Math.atan2(vec1.y,vec1.x)-Math.atan2(rocket.getVelocity().y,rocket.getVelocity().x));
        if (angle2<angle1) thetaPom = -thetaPom;

       /* if (Math.tan(v0arg)<0){
            thetaPom = -thetaPom;
        }*/


        for (Rocket roc:rockets ){
            r = r0/(1-epsilon*Math.cos(theta-theta0-thetaPom));

            x = (float) ((float) r*Math.cos(theta)) + planet.getPosition().x;
            y = (float) ((float) r*Math.sin(theta)) + planet.getPosition().y;

            roc.setPosition(x,y);
            theta+=increment;

        //    System.out.println(x + " "+y);
        }

     /*   for (Rocket roc:mockedRockets){
            r = r0/(1-epsilon*Math.cos(thetaMock-theta0+thetaPom));

            x = (float) ((float) r*Math.cos(thetaMock)) + planet.getPosition().x;
            y = (float) ((float) r*Math.sin(thetaMock)) + planet.getPosition().y;

            roc.setPosition(x,y);
            thetaMock+=increment;

            //    System.out.println(x + " "+y);
        }*/
        Vector2 temp = new Vector2(rocket.getPosition().x,rocket.getPosition().y);
        temp.sub(rockets.peek().getPosition());
        rocketDistance = temp.len();
      //  System.out.println("UDALJENOST ZEMLJE " +l/(1+e*Math.cos(Math.PI - theta0)));
       // System.out.println("DT" + dt);


    }

    private void move(){
        Rocket roc = rockets.pollFirst();
        double r = r0/(1-epsilon*Math.cos(theta-theta0-thetaPom));
        float x = (float) ((float) r*Math.cos(theta)) + planet.getPosition().x;
        float y = (float) ((float) r*Math.sin(theta)) + planet.getPosition().y;

        roc.setPosition(x,y);
        rockets.addLast(roc);
        theta+=increment;
        rocketDistance = (Vector2.dst(roc.getPosition().x,roc.getPosition().y,rocket.getPosition().x,rocket.getPosition().y));

    }
    public List<Rocket> getRockets() {
        return rockets;
    }
}
