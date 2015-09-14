package com.churpi.minicerdo.actors;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by agni_ on 13/09/2015.
 */
public class CameraTarget implements Steerable<Vector2> {

    private Vector2 position;


    public CameraTarget(){
        position = new Vector2();
    }

    @Override
    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position){
        this.position.set(position);
    }

    @Override
    public float getOrientation() {
        return 0;
    }

    @Override
    public Vector2 getLinearVelocity() {
        return null;
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
        return position.cpy();
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
        return 0;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {

    }

    @Override
    public float getMaxLinearAcceleration() {
        return 0;
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
