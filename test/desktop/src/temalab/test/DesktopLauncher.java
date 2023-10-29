package temalab.test;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import temalab.Test;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("test");
		//config.setFullscreenMode(Lwjgl3ApplicationConfiguration.getDisplayMode(Gdx.graphics.getMonitor()));
		config.setWindowedMode(1000, 1000);
		
		new Lwjgl3Application(new Test(), config);
	}
}
