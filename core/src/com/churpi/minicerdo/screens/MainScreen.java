package com.churpi.minicerdo.screens;

//import com.badlogic.gdx.scenes.scene2d

import com.badlogic.gdx.Gdx;
import com.churpi.minicerdo.MinicerdoGame;
import com.churpi.minicerdo.behaviors.InputGameProcessor;
import com.churpi.minicerdo.layers.CityLayer;

/**
 * Created by agni_ on 06/09/2015.
 */
public class MainScreen extends GenericScreen{

    CityLayer city;

    InputGameProcessor gameProcessor;


    public MainScreen(MinicerdoGame game) {
        super(game);

        city = new CityLayer(game);

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
