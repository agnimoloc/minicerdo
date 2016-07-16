package com.churpi.minicerdo.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.churpi.minicerdo.MinicerdoGame;

/**
 * Created by agni_ on 09/09/2015.
 */
public abstract class GenericActor {

    MinicerdoGame game;
    Body body;

    public GenericActor(MinicerdoGame game){
        this.game = game;
    }

    public Body getBody(){
        return body;
    }

    abstract void act(float delta);

    abstract void draw(SpriteBatch batch);

    abstract void dispose();

}
