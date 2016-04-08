package com.churpi.minicerdo.screens;

//import com.badlogic.gdx.scenes.scene2d

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.World;
import com.churpi.minicerdo.MinicerdoGame;
import com.churpi.minicerdo.behaviors.InputGameProcessor;
import com.churpi.minicerdo.layers.CityLayer;
import com.churpi.minicerdo.layers.LoadingLayer;

/**
 * Created by agni_ on 06/09/2015.
 */
public class MainScreen extends GenericScreen{

    CityLayer city;
    LoadingLayer loadingLayer;

    InputGameProcessor gameProcessor;

    public MainScreen(MinicerdoGame game) {
        super(game);

        city = new CityLayer(game);
        loadingLayer = new LoadingLayer(game);

        gameProcessor = new InputGameProcessor();
        Gdx.input.setInputProcessor(gameProcessor);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        city.act(delta);
        city.draw();

        loadingLayer.render(delta);

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {


    }
}
