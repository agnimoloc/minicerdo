package com.churpi.minicerdo.behaviors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.churpi.minicerdo.constants.TypeMessages;

/**
 * Created by igoraviles on 3/27/16.
 */
public class InputGameProcessor implements InputProcessor, Telegraph, GestureDetector.GestureListener {


    MessageManager messageManager;
    //float firstX;
    //float firstY;


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
        //firstX = screenX;
        //firstY = screenY;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        /*float deltax = screenX - firstX;
        float deltay = screenY - firstY;
        Gdx.app.log("drag","x:"+ String.valueOf(deltax) + " y:"+ String.valueOf(deltay));
        float deltaXAbs =Math.abs(deltax);
        float deltaYAbs = Math.abs(deltay);
        if(deltaXAbs > 50 || deltaYAbs > 50) {
            if (deltaXAbs < deltaYAbs) {
                if (deltax < 0)
                    messageManager.dispatchMessage(this, TypeMessages.GAME_SPEED_DOWN);
                else
                    messageManager.dispatchMessage(this, TypeMessages.GAME_SPEED_UP);
            } else {
                if (deltay > 0)
                    messageManager.dispatchMessage(this, TypeMessages.GAME_CHANGE_LEFT);
                else
                    messageManager.dispatchMessage(this, TypeMessages.GAME_CHANGE_RIGHT);
            }
        }*/
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

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        Gdx.app.log("drag","x:"+ String.valueOf(velocityX) + " y:"+ String.valueOf(velocityY));
        float velXAbs = Math.abs(velocityX);
        float velYAbs = Math.abs(velocityY);
        if(velXAbs > 200 || velYAbs > 200) {
            if (velXAbs < velYAbs) {
                if (velocityY > 0)
                    messageManager.dispatchMessage(this, TypeMessages.GAME_SPEED_DOWN);
                else
                    messageManager.dispatchMessage(this, TypeMessages.GAME_SPEED_UP);
            } else {
                if (velocityX < 0)
                    messageManager.dispatchMessage(this, TypeMessages.GAME_CHANGE_LEFT);
                else
                    messageManager.dispatchMessage(this, TypeMessages.GAME_CHANGE_RIGHT);
            }
        }
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){

        return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){

        return false;
    }
}
