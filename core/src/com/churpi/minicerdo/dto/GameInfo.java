package com.churpi.minicerdo.dto;

/**
 * Created by gdlfravi on 7/25/16.
 */
public class GameInfo {
    private int version;
    private GameLevel[] gameLevels;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public GameLevel[] getGameLevels() {
        return gameLevels;
    }

    public void setGameLevels(GameLevel[] gameLevels) {
        this.gameLevels = gameLevels;
    }

}
