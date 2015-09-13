package com.churpi.minicerdo;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by agni_ on 09/09/2015.
 */
public final class Utils {

    private static final float ppm = 16;
    private static float meters;
    private static float pixels;

    public static float pixelsToMeters(float pixels){
        Utils.pixels = pixels;
        return pixels / ppm;
    }

    public static float metersToPixels(float meters){
        Utils.meters = meters;
        return meters * ppm;
    }

    public static float vectorToAngle (Vector2 vector) {
        return (float)Math.atan2(-vector.x, vector.y);
    }

    public static Vector2 angleToVector (Vector2 outVector, float angle) {
        outVector.x = -(float)Math.sin(angle);
        outVector.y = (float)Math.cos(angle);
        return outVector;
    }
}
