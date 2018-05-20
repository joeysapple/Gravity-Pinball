package com.matija.gravitydemo1.states;

import com.badlogic.gdx.assets.AssetManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Korisnik on 28.4.2018..
 */

public class Assets {

    public static final String rocket = "rocketRight2.png";
    public static final String jupiter = "jupiter.png";
    public static final String background = "space.jpg";
    public static final String redBackground = "nebula10green.png";
    public static final List<String> earth = earthStrings();
    public static final List<String> weirdPlanet = weirdStrings();
    public static final List<String> moon = moonStrings();
    public static AssetManager manager = new AssetManager();

    private static ArrayList<String> earthStrings() {
        ArrayList<String> strings = new ArrayList<String>();

        StringBuilder fileSB = new StringBuilder("earth/frame1");
        int index = fileSB.lastIndexOf("/");
        String path = fileSB.substring(0, index + 1);

        for (int i = 0; i < 50; i++) {
            StringBuilder sb = new StringBuilder(path);
            sb.append("frame (" + (i + 1) + ").png");
            strings.add(sb.toString());

        }

        return strings;
    }

    private static ArrayList<String> weirdStrings() {
        ArrayList<String> strings = new ArrayList<String>();

        StringBuilder fileSB = new StringBuilder("weirdPlanet/frame1");
        int index = fileSB.lastIndexOf("/");
        String path = fileSB.substring(0, index + 1);

        for (int i = 0; i < 50; i++) {
            StringBuilder sb = new StringBuilder(path);
            sb.append("frame (" + (i + 1) + ").png");
            System.out.println(sb.toString());
            strings.add(sb.toString());

        }

        return strings;
    }

    private static ArrayList<String> moonStrings(){
        ArrayList<String> strings = new ArrayList<String>();

        StringBuilder fileSB = new StringBuilder("moon/frame1");
        int index = fileSB.lastIndexOf("/");
        String path = fileSB.substring(0, index + 1);

        for (int i = 0; i < 50; i++) {
            StringBuilder sb = new StringBuilder(path);
            sb.append("frame (" + (i + 1) + ").png");
            System.out.println(sb.toString());
            strings.add(sb.toString());

        }

        return strings;
    }

    public static void newManager(){
        manager.dispose();
        manager = new AssetManager();
    }

}
