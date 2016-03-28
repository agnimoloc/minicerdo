package com.churpi.minicerdo.behaviors;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.LookWhereYouAreGoing;
import com.badlogic.gdx.ai.steer.limiters.NullLimiter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.churpi.minicerdo.actors.CameraTarget;
import com.churpi.minicerdo.behaviors.accessors.CameraAccessor;

/**
 * Created by agni_ on 09/09/2015.
 */
public class CameraBehavior implements Steerable<Vector2>{

    OrthographicCamera camera;
    Steerable<Vector2> focusPoint;
    Vector2 velocity;
    CameraTarget targetPoint;

    Arrive<Vector2> steeringBehavior = null;
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

        camera.zoom = 1;

        //Tween.set(camera, CameraAccessor.ZOOM).target(50);

        //Tween.to(camera,CameraAccessor.ZOOM, 1).target(5f).ease(TweenEquations.easeInOutElastic).start(tweenManager);

        //tweenManager.killTarget(camera);
    }

    public void setTargetPoint(Vector2 position){

        targetPoint.setPosition(position);
        steeringBehavior.setTarget(targetPoint);
    }

    public void forceTargetPoint(){
        camera.position.set(targetPoint.getPosition(), camera.zoom);
    }

    public void update(float delta){

        //steeringBehavior.setTarget(focusPoint);
        steeringBehavior.calculateSteering(steeringOutput);

        velocity.mulAdd(steeringOutput.linear, delta);
        camera.position.add(velocity.x, velocity.y, 0);


        //camera.position.set(targetPoint.getPosition().x, targetPoint.getPosition().y, 0);

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
}
