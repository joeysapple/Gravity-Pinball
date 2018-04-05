package com.matija.gravitydemo1.utilities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Korisnik on 23.3.2018..
 */

public class ShapeFactory {
    public static Texture createPixmapRoundCornerRect(Color color, int width,
                                                      int height, int radius) {
        Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillCircle(radius, radius, radius);
        pixmap.fillCircle(width - radius, radius, radius);
        pixmap.fillCircle(width - radius, height - radius, radius);
        pixmap.fillCircle(radius, height - radius, radius);
        pixmap.fillRectangle(0, radius, width, height - (radius * 2));

        pixmap.fillRectangle(radius, 0, width - (radius * 2), height);
        Texture pixmaptex = new Texture(pixmap);
        pixmap.dispose();
        return pixmaptex;
    }
}
