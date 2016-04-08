package com.churpi.minicerdo.workers;

import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.ai.msg.Telegraph;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.churpi.minicerdo.constants.TypeMessages;

/**
 * Created by igoraviles on 4/7/16.
 */
public class AssetManagerWorker implements Telegraph {

    private AssetManager assetManager;
    private MessageManager messageManager;
    private String MAP_EXTENSION = ".tmx";

    public AssetManagerWorker(){

        messageManager = MessageManager.getInstance();
        messageManager.addListener(this, TypeMessages.ASS_LOAD_MAP);
        messageManager.addListener(this, TypeMessages.ASS_LOAD_ATLAS);
        messageManager.addListener(this, TypeMessages.ASS_LOAD_FONT);
        messageManager.addListener(this, TypeMessages.ASS_LOAD_MUSIC);
        messageManager.addListener(this, TypeMessages.ASS_LOAD_SOUND);

    }

    private AssetManager getAssetManager(){
        if(assetManager == null){
            assetManager = new AssetManager();
            assetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        }
        return assetManager;
    }

    public void loadMap(String name){
        getAssetManager().load(name + MAP_EXTENSION, TiledMap.class);
    }

    public void update(){
        if(assetManager != null) {
            if (assetManager.update()) {
                assetManager.finishLoading();
                assetManager.dispose();
                assetManager = null;
                messageManager.dispatchMessage(this, TypeMessages.ASS_FINISH, this);
            } else {
                messageManager.dispatchMessage(this, TypeMessages.ASS_PROGRESS, assetManager.getProgress());
            }
        }
    }

    private String mapPath(String name){
        return name.concat(MAP_EXTENSION);
    }

    public TiledMap getMap(String name){
        if(assetManager != null && assetManager.isLoaded(mapPath(name))) {
            return assetManager.get(mapPath(name), TiledMap.class);
        }else{
            throw new IllegalArgumentException("The asset " + name + " is not loaded yet");
        }
    }


    @Override
    public boolean handleMessage(Telegram msg) {

        switch (msg.message){
            case TypeMessages.ASS_LOAD_MAP:
                loadMap((String)msg.extraInfo);
                return true;
        }


        return false;
    }
}
