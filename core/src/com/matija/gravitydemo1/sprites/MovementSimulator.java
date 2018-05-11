package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.math.Vector2;
import com.matija.gravitydemo1.states.PlayState;

/**
 * Created by Korisnik on 24.4.2018..
 */

public class MovementSimulator {
    private Vector2 objectPosition;
    private Vector2 objectVelocity;
    private Vector2 objectAcceleration;
    private double objectMass;

    private Vector2 planetPosition;
    private double planetMass;


    public void move(Movable object, Planet planet, float dt){
        objectInit(object);
        planetInit(planet);

        Vector2 posDiff = new Vector2(objectPosition.x,objectPosition.y);
        posDiff.sub(planetPosition);
        double posDiffLength2 = posDiff.len2();
        posDiff.setLength((float) ((float) PlayState.G*planet.getMass()/posDiffLength2)).scl(-1);
        posDiff.add(objectAcceleration);

        objectPosition.add(objectVelocity.x*dt,objectVelocity.y*dt)
                .add((float)0.5*dt*dt*posDiff.x,(float)0.5*dt*dt*posDiff.y);

        objectVelocity.add(posDiff.x*dt,posDiff.y*dt);
    }

    private void objectInit(Movable object){
        objectPosition = object.getPosition();
        objectVelocity = object.getVelocity();
        objectAcceleration = object.getAcceleration();
    }

    private void planetInit(Planet planet){
        planetPosition = planet.getPosition();
        planetMass = planet.getMass();
    }
}
