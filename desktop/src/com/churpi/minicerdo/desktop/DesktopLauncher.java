package com.churpi.minicerdo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.churpi.minicerdo.MinicerdoGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		TexturePacker.Settings settings = new TexturePacker.Settings();
		TexturePacker.process("../../images/loading","../../android/assets","loading");
		TexturePacker.process("../../images/skin","../../android/assets","skin");


		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 640;
		new LwjglApplication(new MinicerdoGame(), config);
	}
}
