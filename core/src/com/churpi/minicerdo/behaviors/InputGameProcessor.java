package com.churpi.minicerdo.behaviors;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.churpi.minicerdo.messages.GameMessages;

/**
 * Created by igoraviles on 3/27/16.
 */
public class InputGameProcessor implements InputProcessor, Telegraph {


    MessageManager messageManager;



    public InputGameProcessor(){
        super();

        messageManager = MessageManager.getInstance();
    }

    @Override
    public boolean keyDown(int keycode) {


        if(keycode== Input.Keys.DOWN) {
            messageManager.dispatchMessage(this, GameMessages.SPEED_DOWN);
        }
        if(keycode == Input.Keys.UP){
            messageManager.dispatchMessage(this, GameMessages.SPEED_UP);
        }
        if(keycode == Input.Keys.LEFT){
            messageManager.dispatchMessage(this, GameMessages.CHANGE_LEFT);
        }
        if(keycode == Input.Keys.RIGHT){
            messageManager.dispatchMessage(this, GameMessages.CHANGE_RIGHT);
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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
