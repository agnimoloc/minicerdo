package com.churpi.minicerdo.behaviors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.churpi.minicerdo.constants.TypeMessages;

/**
 * Created by igoraviles on 3/27/16.
 */
public class InputGameProcessor implements InputProcessor, Telegraph {


    MessageManager messageManager;
    float firstX;
    float firstY;


    public InputGameProcessor(){
        super();

        messageManager = MessageManager.getInstance();
    }

    @Override
    public boolean keyDown(int keycode) {


        if(keycode== Input.Keys.DOWN) {
            messageManager.dispatchMessage(this, TypeMessages.GAME_SPEED_DOWN);
        }
        if(keycode == Input.Keys.UP){
            messageManager.dispatchMessage(this, TypeMessages.GAME_SPEED_UP);
        }
        if(keycode == Input.Keys.LEFT){
            messageManager.dispatchMessage(this, TypeMessages.GAME_CHANGE_LEFT);
        }
        if(keycode == Input.Keys.RIGHT){
            messageManager.dispatchMessage(this, TypeMessages.GAME_CHANGE_RIGHT);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        firstX = screenX;
        firstY = screenY;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        float deltax = screenX - firstX;
        float deltay = screenY - firstY;
        if(Math.abs(deltax) > Math.abs(deltay)){
            if(deltax > 0)
                messageManager.dispatchMessage(this, TypeMessages.GAME_SPEED_DOWN);
            else
                messageManager.dispatchMessage(this, TypeMessages.GAME_SPEED_UP);
        }else{
            if(deltax > 0)
                messageManager.dispatchMessage(this, TypeMessages.GAME_CHANGE_LEFT);
            else
                messageManager.dispatchMessage(this, TypeMessages.GAME_CHANGE_RIGHT);
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean handleMessage(Telegram msg) {
        return false;
    }
}
