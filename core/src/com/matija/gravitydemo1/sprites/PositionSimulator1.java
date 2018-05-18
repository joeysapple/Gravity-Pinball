package com.matija.gravitydemo1.sprites;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.matija.gravitydemo1.states.PlayState;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Korisnik on 4.5.2018..
 */

public class PositionSimulator1 {
    private List<Point> points;
    private Vector2 lastVelocity;
    private Vector2 lastAcceleration;
    private Vector2 lastPosition;
    private Planet planet;
    private Rocket rocket;
    private int index;
    private Vector2 initPoint;
    private double  currDist = 0;
    private boolean initialized = false;

    public PositionSimulator1(){
        points = new ArrayList<Point>();

        for (int i=0;i<20;i++){
            Point p = new Point(new Vector2(0,0));

            points.add(p);
        }

        lastVelocity = new Vector2(0,0);
        lastAcceleration = new Vector2(0,0);
        lastPosition = new Vector2(0,0);
        initPoint = new Vector2(0,0);
        index=0;
        initialized = false;

    }

    public void setPlanet(Planet planet){
        this.planet=planet;
    }

    public void setRocket(Rocket rocket){
        this.rocket=rocket;
    }

    public void simulate(float dt){
        Vector2 acceleration = rocket.getAcceleration();

        if (acceleration.x==0 && acceleration.y==0){

           if (initialized && points.get(0).getBounds().overlaps(rocket.getBounds())){
               move(0.01f);
               System.out.println("pozvan move");
               return;
           }

        }
        else{
            newTrajectory();
            return;
        }

        if (initialized==false){

            newTrajectory();
            initialized=true;
        }

    }

    private void newTrajectory(){

        lastVelocity.set(rocket.getVelocity().x,rocket.getVelocity().y);
        lastAcceleration.set(rocket.getAcceleration().x,rocket.getAcceleration().y);
        lastPosition.set(rocket.getPosition().x,rocket.getPosition().y);
        initPoint.set(points.get(0).getPosition().x,points.get(0).getPosition().y);
        index=-1;
        Vector2 posDiff = new Vector2(lastPosition.x,lastPosition.y);
       // points.get(0).setPosition(lastPosition.x,lastPosition.y);
        double timeLimit = 20.0;
        double dist = 20.0;
        float dt=0.01f;
        double currDist = dist;
        double accDist = 0;
        float prevX,prevY;
        this.initializePosition(dt);
        while (accDist<300){
            prevX = lastPosition.x;
            prevY = lastPosition.y;

            this.nextPosition(dt);
            currDist+=Vector2.dst(prevX,prevY,lastPosition.x,lastPosition.y);

            if (currDist>=dist){
                index++;
                this.checkIndexBound();

                points.get(index).setPosition(lastPosition.x,lastPosition.y);
                accDist+=currDist;
                currDist=0;
            }
        }
        System.out.println("Zadnja" + lastPosition.x + " "+lastPosition.y);
        this.currDist = currDist;
    }

    private void move(float dt){
        Point firstPoint = points.get(0);

        float prevX,prevY;
        System.out.println("Prva" + lastPosition.x + " "+lastPosition.y);
        while (currDist<20){
            prevX = lastPosition.x;
            prevY = lastPosition.y;
            this.nextPosition(dt);
            currDist+=Vector2.dst(prevX,prevY,lastPosition.x,lastPosition.y);
        }


        System.out.println("prije micanje "+ points.get(0).getPosition().x + " "+points.get(0).getPosition().y);
        points.remove(0);
        System.out.println("poslijr" + " micanje "+ points.get(0).getPosition().x + " "+points.get(0).getPosition().y);
        firstPoint.setPosition(lastPosition.x,lastPosition.y);

        int j = 1;
        for (Point p:points){

            System.out.println(j++ + ":   "+p.getPosition().x + " " +p.getPosition().y);
        }
        System.out.println("Pise" + lastPosition.x + " " +lastPosition.y);
        points.add(index,firstPoint);
        currDist=0;

    }

    private void initializePosition(float dt){
        Circle rocketBound = rocket.getBounds();
        while (Vector2.dst(lastPosition.x,lastPosition.y,rocketBound.x,rocketBound.y)<=rocketBound.radius){
            nextPosition(dt);
        }

        float boundX = lastPosition.x;
        float boundY = lastPosition.y;

        double dist = 0;
        float oldX,oldY;
        oldX = lastPosition.x;
        oldY = lastPosition.y;

        while (dist<10){
            nextPosition(dt);
            dist = dist + Vector2.dst(lastPosition.x,lastPosition.y,oldX,oldY);
            oldX = lastPosition.x;
            oldY = lastPosition.y;
        }

     /*   if (initialized==false){
            while (Vector2.dst(boundX,boundY,lastPosition.x,lastPosition.y)<10){
                nextPosition(dt);
            }
        }
        else{
            double oldDist = Vector2.dst(lastPosition.x,lastPosition.y,initPoint.x,initPoint.y);
            nextPosition(dt);
            double newDist = Vector2.dst(lastPosition.x,lastPosition.y,initPoint.x,initPoint.y);
            while(newDist<oldDist && Vector2.dst(lastPosition.x,lastPosition.y,boundX,boundY)<10){
                nextPosition(dt);
                oldDist = newDist;
                newDist = Vector2.dst(lastPosition.x,lastPosition.y,initPoint.x,initPoint.y);
            }
        }*/

    }
    private void nextPosition(float dt){

        Vector2 posDiff = new Vector2(lastPosition.x,lastPosition.y);
        posDiff.sub(planet.getPosition());
        double posDiffLength2 = posDiff.len2();
        posDiff.setLength((float) ((float) PlayState.G*planet.getMass()/posDiffLength2)).scl(-1);
        lastPosition.add(lastVelocity.x*dt,lastVelocity.y*dt)
                .add((float)0.5*dt*dt*posDiff.x,(float)0.5*dt*dt*posDiff.y);
        lastVelocity.add(posDiff.x*dt,posDiff.y*dt);

    }

    private void checkIndexBound(){
        if (index>=points.size()){
            points.add(new Point(new Vector2(0,0)));
        }
    }
    public List<Point> getRockets(){
        return this.points;
    }

    public void dispose(){
        for (Point p:points){
            p.dispose();
        }
    }

    public int getIndex(){
        return this.index;
    }

    public void setInitialized(boolean initialized){
        this.initialized=initialized;
    }
}
