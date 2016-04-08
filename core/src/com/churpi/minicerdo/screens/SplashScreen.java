package com.churpi.minicerdo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.churpi.minicerdo.MinicerdoGame;
import com.churpi.minicerdo.constants.TypeMessages;

/**
 * Created by igoraviles on 4/7/16.
 */
public class SplashScreen extends GenericScreen implements Telegraph {

    MessageManager messageManager;
    public SplashScreen(MinicerdoGame game) {
        super(game);

        initMessages();
    }

    private void initMessages(){
        messageManager = MessageManager.getInstance();

        messageManager.addListeners(this, TypeMessages.ASS_PROGRESS, TypeMessages.ASS_FINISH);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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
        messageManager.removeListener(this, TypeMessages.ASS_PROGRESS, TypeMessages.ASS_FINISH);
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        switch (msg.message){
            case TypeMessages.ASS_FINISH:
                Gdx.app.log("Debug", "Finish");
                return true;
            case TypeMessages.ASS_PROGRESS:
                Gdx.app.log("Debug", ((Float)msg.extraInfo).toString());
                return true;
        }
        return false;
    }
}
