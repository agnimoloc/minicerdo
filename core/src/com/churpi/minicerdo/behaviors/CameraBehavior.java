package com.churpi.minicerdo.behaviors;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.churpi.minicerdo.behaviors.accessors.CameraAccessor;

/**
 * Created by agni_ on 09/09/2015.
 */
public class CameraBehavior {

    OrthographicCamera camera;

    public OrthographicCamera getOrthographicCamera(){
        return camera;
    }

    public CameraBehavior(OrthographicCamera camera, TweenManager tweenManager){
        this.camera = camera;
        Tween.registerAccessor(OrthographicCamera.class, new CameraAccessor());
        Tween.set(camera, CameraAccessor.ZOOM).target(1);
        Tween.to(camera,CameraAccessor.ZOOM, 1).target(0.5f).ease(TweenEquations.easeInOutElastic).start(tweenManager);
    }

    public void update(){


        camera.update();
    }

    public Matrix4 getProjection(){
        return camera.combined;
    }

}
