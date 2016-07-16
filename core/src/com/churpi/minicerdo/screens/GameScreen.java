package com.churpi.minicerdo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.churpi.minicerdo.MinicerdoGame;
import com.churpi.minicerdo.behaviors.InputGameProcessor;
import com.churpi.minicerdo.layers.CityLayer;

/**
 * Created by igoraviles on 7/3/16.
 */
public class GameScreen extends GenericScreen{

    CityLayer city;

    InputGameProcessor gameProcessor;


    public GameScreen(MinicerdoGame game) {
        super(game);

        city = new CityLayer(game);

        gameProcessor = new InputGameProcessor();
        //Gdx.input.setInputProcessor( gameProcessor);

        Gdx.input.setInputProcessor(new GestureDetector(gameProcessor));

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
        city.dispose();
    }
}