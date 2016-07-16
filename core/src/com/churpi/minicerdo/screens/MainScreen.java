package com.churpi.minicerdo.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.churpi.minicerdo.MinicerdoGame;
import com.churpi.minicerdo.constants.GameEngine;

/**
 * Created by agni_ on 06/09/2015.
 */
public class MainScreen extends GenericScreen{

    Stage stage;
    Table table;

    public MainScreen(final MinicerdoGame game) {
        super(game);

        stage = new Stage(new FitViewport(GameEngine.VIEWPORT_UI_WIDTH, GameEngine.VIEWPORT_UI_HEIGHT));

        stage.setDebugAll(true);
        Skin skin = game.getAssetManager().get("skin.json", Skin.class);

        table = new Table();
        table.setPosition(GameEngine.VIEWPORT_UI_WIDTH/2,GameEngine.VIEWPORT_UI_HEIGHT/2);


        TextButton button = new TextButton("Test", skin);
        button.setHeight(100);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        TextButton button2 = new TextButton("Test2", skin);
        button.setHeight(100);
        TextButton button3 = new TextButton("Test2", skin);
        button.setHeight(100);
        TextButton button4 = new TextButton("Test2", skin);
        button.setHeight(100);

        table.add(button);
        table.row();
        table.add(button2);
        table.row();
        table.add(button3);
        table.row();
        table.add(button4);

        stage.addActor(table);

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(stage);

        Gdx.input.setInputProcessor(multiplexer);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height, true);

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
        stage.dispose();

    }
}
