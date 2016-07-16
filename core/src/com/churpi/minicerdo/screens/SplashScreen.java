package com.churpi.minicerdo.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.churpi.minicerdo.MinicerdoGame;
import com.churpi.minicerdo.constants.AssetsLoadTypes;
import com.churpi.minicerdo.constants.GameEngine;
import com.churpi.minicerdo.constants.TypeMessages;
import jdk.nashorn.internal.codegen.CompilerConstants;

/**
 * Created by igoraviles on 4/7/16.
 */
public class SplashScreen extends GenericScreen implements Telegraph {

    MessageManager messageManager;
    Stage stage;

    private Image logo;
    private Image loadingFrame;
    private Image loadingBarHidden;
    private Image screenBg;
    private Image loadingBg;

    private float startX, endX;
    private float percent = 0;

    private Image loadingBar;
    private LoadingCallback callbackLoaded;

    public interface LoadingCallback{
        void assetLoaded(MinicerdoGame game);
    }

    public SplashScreen(MinicerdoGame game, int AssetLoadType, LoadingCallback CallbackLoaded, String... Params) {
        super(game);

        stage = new Stage(new FitViewport(GameEngine.VIEWPORT_UI_WIDTH, GameEngine.VIEWPORT_UI_HEIGHT));

        if(!game.getAssetManager().isLoaded("loading.atlas")) {
            game.getAssetManager().load("loading.atlas", TextureAtlas.class);
            game.getAssetManager().load("skin.json", Skin.class);
            game.getAssetManager().finishLoading();
        }
        TextureAtlas atlas = game.getAssetManager().get("loading.atlas");


        logo = new Image(atlas.findRegion("badlogic"));
        loadingFrame = new Image(atlas.findRegion("loading-frame"));
        loadingBarHidden = new Image(atlas.findRegion("loading-bar-hidden"));
        screenBg = new Image(atlas.findRegion("screen-bg"));
        loadingBg = new Image(atlas.findRegion("loading-frame-bg"));
        loadingBar = new Image(atlas.findRegion("loading-bar1"));

        stage.addActor(screenBg);
        stage.addActor(logo);
        stage.addActor(loadingBar);
        stage.addActor(loadingBg);
        stage.addActor(loadingBarHidden);
        stage.addActor(loadingFrame);


        //initMessages();


        callbackLoaded = CallbackLoaded;

        //loadAssets(AssetLoadType, Params);
        // Add everything to be loaded, for instance:
        // game.manager.load("data/assets1.pack", TextureAtlas.class);
        // game.manager.load("data/assets2.pack", TextureAtlas.class);
        // game.manager.load("data/assets3.pack", TextureAtlas.class);
    }

    private void loadAssets(int AssetLoadType, String... Params){
        switch (AssetLoadType){
            case AssetsLoadTypes.BASIC:

                break;
            case AssetsLoadTypes.MAP:
                loadMap(Params[0]);
                break;
        }
    }

    private void loadMap(String mapId){
        //Load map...
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



        if (game.getAssetManager().update()) { // Load some, will return true if done loading
            callbackLoaded.assetLoaded(game);
        }

        // Interpolate the percentage to make it more smooth
        percent += delta/2;
        if(percent > 1f){
            percent = 1f;

        }else{
            Gdx.app.log("percent", String.format("aaa %f", percent));
        }

        //percent = Interpolation.linear.apply(percent, game.manager.getProgress(), 0.1f);

        // Update positions (and size) to match the percentage
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();

        // Show the loading screen
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Set our screen to always be XXX x 480 in size
        /*width = 400 * width / height;
        height = 600;*/
        stage.getViewport().update(width, height,true);
        width = 400;
        height = 600;

        int barsize = 40;
        // Make the background fill the screen
        screenBg.setSize(width, height);

        // Place the logo in the middle of the screen and 100 px up
        logo.setX((width - logo.getWidth()) / 2);
        logo.setY((height - logo.getHeight()) / 2);

        loadingFrame.setSize(width,barsize);
        loadingBar.setSize(width,barsize -5);
        loadingBarHidden.setHeight(barsize);

        // Place the loading frame in the middle of the screen
        loadingFrame.setX((width - loadingFrame.getWidth()) / 2);
        //loadingFrame.setY((height - loadingFrame.getHeight()) / 2);
        loadingFrame.setY(loadingFrame.getHeight()/2);


        // Place the loading bar at the same spot as the frame, adjusted a few px
        loadingBar.setX(loadingFrame.getX() + 15);
        loadingBar.setY(loadingFrame.getY() + 3);

        // Place the image that will hide the bar on top of the bar, adjusted a few px
        loadingBarHidden.setX(loadingBar.getX() + 35);
        loadingBarHidden.setY(loadingBar.getY() - 3);
        // The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = 400;

        // The rest of the hidden bar
        loadingBg.setSize(width, barsize-5);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);
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
        stage.dispose();
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
