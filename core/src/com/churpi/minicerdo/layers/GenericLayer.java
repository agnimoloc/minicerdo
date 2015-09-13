package com.churpi.minicerdo.layers;

import com.churpi.minicerdo.MinicerdoGame;
import com.churpi.minicerdo.screens.GenericScreen;

/**
 * Created by agni_ on 09/09/2015.
 */
public abstract class GenericLayer {

    MinicerdoGame game;

    public GenericLayer (MinicerdoGame game){
        this.game = game;
    }
}
