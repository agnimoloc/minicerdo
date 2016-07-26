package com.churpi.minicerdo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.utils.Json;
import com.churpi.minicerdo.MinicerdoGame;
import com.churpi.minicerdo.dto.GameInfo;
import com.churpi.minicerdo.dto.GameLevel;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class DesktopLauncher {
	public static void main (String[] arg) {
		TexturePacker.Settings settings = new TexturePacker.Settings();
		TexturePacker.process("../../images/loading","../../android/assets","loading");
		TexturePacker.process("../../images/skin","../../android/assets","skin");

		//createJsonSample();

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 640;
		new LwjglApplication(new MinicerdoGame(), config);
	}

    private static void createJsonSample(){
        GameInfo info = new GameInfo();
        info.setVersion(1);
        GameLevel[] levels = new GameLevel[1];
        levels[0] = new GameLevel();
        levels[0].setName("Test");
        levels[0].setDescription("Test level 1");
        levels[0].setVersion(1);
        info.setGameLevels(levels);

        Json json = new Json();
        String jsonText = json.toJson(info);
        FileWriter writer;
        try {
            writer = new FileWriter("../../images/minicerdoconfig.json");
            writer.write(jsonText);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
