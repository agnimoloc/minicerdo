package com.churpi.minicerdo.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.World;
import com.churpi.minicerdo.MinicerdoGame;

/**
 * Created by agni_ on 06/09/2015.
 */
public abstract class GenericScreen implements Screen {
    MinicerdoGame game;

    public GenericScreen(MinicerdoGame game){
        this.game = game;
    }

    public MinicerdoGame getGame(){
        return game;
    }

}
