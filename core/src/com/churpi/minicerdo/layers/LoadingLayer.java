package com.churpi.minicerdo.layers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.churpi.minicerdo.MinicerdoGame;

/**
 * Created by igoraviles on 4/7/16.
 */
public class LoadingLayer extends GenericLayer {
    Sprite sprite;
    SpriteBatch batch;
    OrthographicCamera camera;
    public LoadingLayer(MinicerdoGame game) {
        super(game);
        sprite = new Sprite(new Texture("badlogic.jpg"));
        sprite.setSize(30,30);
        sprite.setCenter(15,15);
        sprite.setPosition(-15,-15);
        batch = game.getBatch();
        camera = game.getUICamera();
    }

    public void render(float delta){

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(sprite,sprite.getX(),sprite.getY(),sprite.getWidth(), sprite.getHeight());
        batch.end();
    }
}
