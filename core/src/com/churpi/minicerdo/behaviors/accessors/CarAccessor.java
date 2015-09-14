package com.churpi.minicerdo.behaviors.accessors;

import aurelienribon.tweenengine.TweenAccessor;
import com.churpi.minicerdo.actors.CarActor;

/**
 * Created by agni_ on 13/09/2015.
 */
public class CarAccessor implements TweenAccessor<CarActor> {

    public final static int ACCELERATIONSPEED = 0;
    public final static int ACCELERATION = 1;
    public final static int SPEED = 2;
    @Override
    public int getValues(CarActor carActor, int tweenType, float[] values) {
        switch (tweenType){
            case ACCELERATIONSPEED:
                values[0] = carActor.getMaxLinearAcceleration();
                values[1] = carActor.getMaxLinearSpeed();
                return 1;
            case ACCELERATION:
                values[0] = carActor.getMaxLinearAcceleration();
                return 1;
            case SPEED:
                values[0] = carActor.getMaxLinearSpeed();
                return 1;
        }
        return 0;
    }

    @Override
    public void setValues(CarActor carActor, int tweenType, float[] values) {
        switch (tweenType){
            case ACCELERATIONSPEED:
                carActor.setMaxLinearAcceleration(values[0]);
                carActor.setMaxLinearSpeed(values[1]);
            case ACCELERATION:
                carActor.setMaxLinearAcceleration(values[0]);
            case SPEED:
                carActor.setMaxLinearSpeed(values[0]);
        }
    }
}
