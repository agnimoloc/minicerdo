package com.churpi.minicerdo.behaviors;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.churpi.minicerdo.actors.CameraTarget;
import com.churpi.minicerdo.behaviors.accessors.CameraAccessor;
import com.churpi.minicerdo.constants.TypeMessages;

/**
 * Created by agni_ on 09/09/2015.
 */
public class CameraBehavior implements Steerable<Vector2>, Telegraph{

    OrthographicCamera camera;
    Steerable<Vector2> focusPoint;
    Vector2 velocity;
    CameraTarget targetPoint;
    TweenManager tweenManager;

    Arrive<Vector2> steeringBehavior = null;

    int zoomTarget = 1;


    static final float[] ZOOM_LEVELS = { 0.5f, 0.7f, 1f,  1.3f };

    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());


    public OrthographicCamera getOrthographicCamera(){
        return camera;
    }

    public CameraBehavior(OrthographicCamera camera, TweenManager tweenManager){
        this.camera = camera;

        velocity = new Vector2();

        targetPoint = new CameraTarget();

        if(steeringBehavior == null) {
            steeringBehavior = new Arrive<Vector2>(this, targetPoint) //
                    .setTimeToTarget(0.5f) //
                    .setArrivalTolerance(0.1f) //
                    .setDecelerationRadius(5);
        }

        //Tween.set(camera, CameraAccessor.ZOOM).target(10f).start(tweenManager);
        Tween.set(camera, CameraAccessor.ZOOM).target(1.7f).start(tweenManager);
        Tween.to(camera,CameraAccessor.ZOOM, 5).delay(1).target(ZOOM_LEVELS[zoomTarget]).ease(TweenEquations.easeOutBack).start(tweenManager);
        this.tweenManager = tweenManager;

        MessageManager.getInstance().addListener(this, TypeMessages.GAME_SPEED_UP);
        MessageManager.getInstance().addListener(this, TypeMessages.GAME_SPEED_DOWN);

    }

    public void setTargetPoint(Vector2 position){

        targetPoint.setPosition(position);
        steeringBehavior.setTarget(targetPoint);
    }

    public void forceTargetPoint(){
        camera.position.set(targetPoint.getPosition(), camera.zoom);
    }

    public void update(float delta){

        steeringBehavior.calculateSteering(steeringOutput);

        velocity.mulAdd(steeringOutput.linear, delta);
        camera.position.add(velocity.x, velocity.y, 0);

        camera.update();
    }

    public Matrix4 getProjection(){
        return camera.combined;
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(camera.position.x, camera.position.y);
    }

    @Override
    public float getOrientation() {
        return 0;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return velocity;
    }

    @Override
    public float getAngularVelocity() {
        return 0;
    }

    @Override
    public float getBoundingRadius() {
        return 0;
    }

    @Override
    public boolean isTagged() {
        return false;
    }

    @Override
    public void setTagged(boolean tagged) {

    }

    @Override
    public Vector2 newVector() {
        return getPosition().cpy();
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return 0;
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return null;
    }

    @Override
    public float getMaxLinearSpeed() {
        return 1;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {

    }

    @Override
    public float getMaxLinearAcceleration() {
        return 5;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {

    }

    @Override
    public float getMaxAngularSpeed() {
        return 0;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {

    }

    @Override
    public float getMaxAngularAcceleration() {
        return 0;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {

    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message){
            case TypeMessages.GAME_SPEED_DOWN:
                if(zoomTarget > 0) {
                    zoomTarget--;
                    tweenManager.killTarget(camera);
                    Tween.to(camera, CameraAccessor.ZOOM, 2).target(ZOOM_LEVELS[zoomTarget]).ease(TweenEquations.easeOutBack).start(tweenManager);
                }
                break;
            case TypeMessages.GAME_SPEED_UP:
                if(zoomTarget < ZOOM_LEVELS.length - 1) {
                    zoomTarget ++;
                    tweenManager.killTarget(camera);
                    Tween.to(camera, CameraAccessor.ZOOM, 2).target(ZOOM_LEVELS[zoomTarget]).ease(TweenEquations.easeOutBack).start(tweenManager);
                }
                break;
        }
        return false;
    }
}
