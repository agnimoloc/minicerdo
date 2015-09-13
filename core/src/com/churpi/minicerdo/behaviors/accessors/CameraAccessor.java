package com.churpi.minicerdo.behaviors.accessors;


import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created by agni_ on 07/09/2015.
 */
public class CameraAccessor implements TweenAccessor<OrthographicCamera> {

    public static final int ZOOM = 0;
    @Override
    public int getValues(OrthographicCamera camera, int tweenType, float[] returnValues) {

        switch (tweenType){
            case ZOOM:
                returnValues[0]= camera.zoom;
                return 1;
        }

        return 0;
    }

    @Override
    public void setValues(OrthographicCamera camera, int tweenType, float[] newValues) {
        switch (tweenType){
            case ZOOM: camera.zoom = newValues[0]; break;

        }
    }
}
