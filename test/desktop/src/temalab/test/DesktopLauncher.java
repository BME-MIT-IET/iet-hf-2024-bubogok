package temalab.test;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import temalab.Simu;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration simu = new Lwjgl3ApplicationConfiguration();
		simu.setTitle("float");
		simu.setWindowedMode(1600, 1000);
		new Lwjgl3Application(new Simu(), simu);		
	}
}