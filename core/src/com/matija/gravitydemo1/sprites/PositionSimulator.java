package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.matija.gravitydemo1.states.PlayState;

/**
 * Created by Korisnik on 31.3.2018..
 */

/**
 * Klasa koja služi da bi se predvidjela putanja rakete na temelju trenutnih parametara
 */
public class PositionSimulator {
    private Rocket rocket;
    private Planet planet;
    Array<Rocket> rockets;
    Vector2 position;
    Vector2 velocity;
    Vector2 positionNorm;
    Vector2 thetaNorm;
    double planetMass;
    double rocketMass;

    /**
     * Konstruktor
     * @param rocket Raketa za koju se vrši predikcija putanje
     * @param planet Planet čija gravitaija djeluje na raketu
     */
    public PositionSimulator(Rocket rocket, Planet planet){
        this.rocket = rocket;
        rockets = new Array<Rocket>();
        position = new Vector2();
        velocity = new Vector2();
        positionNorm = new Vector2();
        thetaNorm = new Vector2();
        planetMass = planet.getMass();
        rocketMass = rocket.getMass();
        this.planet = planet;

        for (double theta = 0;theta <= Math.PI;theta+=0.1 ){

            rockets.add(new Rocket(new Vector2(0,0), new Vector2(0,0), new Vector2(0,0),1));
            Texture texture = new Texture(Gdx.files.internal("dot.jpg"));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

            rockets.peek().setTexture(new Sprite(texture));
            rockets.peek().setBounds(new com.badlogic.gdx.math.Circle(0,0,2 ));
        }
    }

    /**
     * Metoda koja služi za predviđanje putanje. Koristi se Keplerova jednadžba
     * @param dt
     */
    public void simulate(float dt){
        position.set(rocket.getPosition().x - planet.getPosition().x,rocket.getPosition().y - planet.getPosition().y);
        velocity.set(rocket.getVelocity().x,rocket.getVelocity().y);
        System.out.println("Brzina" + " " + velocity);
        double theta0 = Math.atan2(position.y,position.x);

    //    System.out.println("Theta je " + + theta0);
      //  System.out.println("Pozicija  " + + position.x + " "+position.y);

     //   System.out.println("Pozicija planeta " + + planet.getPosition().x + " "+planet.getPosition().y);
     //   System.out.println("Pozicija RAKETE  " + + rocket.getPosition().x + " "+rocket.getPosition().y);

        positionNorm.set(position.x,position.y);
        positionNorm.setLength(1);
        System.out.println("Pozicija NORMA  " + + positionNorm.x + " "+positionNorm.y);

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
        double epsilon = Math.sqrt(1+(2*E*L*L)/(mi*(PlayState.G*planetMass*rocketMass)*(PlayState.G*planetMass*rocketMass)));
        double r0 = L*L/(mi*PlayState.G*planetMass*rocketMass);

       // System.out.println("A" + A);

      //  System.out.println(e);

        double v0arg = Math.atan2(velocity.y,velocity.x);
        double thetaPom = Math.acos((1-(r0/positionDistance))/epsilon);

        System.out.println("r unit" + " "+ positionNorm);
        System.out.println("theta unit" + " "+ thetaNorm);
        System.out.println("w0" + " "+ angleVelocity);
        System.out.println("theta 0" + " "+ theta0);
        System.out.println("v0 kut" + " "+ v0arg);
        System.out.println("theta pomak" + " "+ thetaPom);
        System.out.println("epsilon " + epsilon);
        System.out.println("E "+ E );
        System.out.println("L "+ L );
        if (Math.tan(v0arg)<0){
            thetaPom = -thetaPom;
        }

        int index = 0;
        double increment = 0.1;
        if (angleVelocity<0) increment=-0.1;
        for (double theta = theta0; index < rockets.size;theta+=increment ){
            double r = r0/(1-epsilon*Math.cos(theta-theta0-thetaPom));

            float x = (float) ((float) r*Math.cos(theta)) + planet.getPosition().x;
            float y = (float) ((float) r*Math.sin(theta)) + planet.getPosition().y;

            rockets.get(index).setPosition(x,y);

        //    System.out.println(x + " "+y);
            index++;
        }
      //  System.out.println("UDALJENOST ZEMLJE " +l/(1+e*Math.cos(Math.PI - theta0)));
       // System.out.println("DT" + dt);


    }

    public Array<Rocket> getRockets() {
        return rockets;
    }
}
