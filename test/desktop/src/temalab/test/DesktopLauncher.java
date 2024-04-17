package temalab.test;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import temalab.*;
import temalab.communicator.MainCommunicator;

public class DesktopLauncher {
	public static void main (String[] arg) {
		MainModel mm = new MainModel();
		MainCommunicator mc = new MainCommunicator(mm);
		if(arg[0].equals("graf")) {
			Lwjgl3ApplicationConfiguration simu = new Lwjgl3ApplicationConfiguration();
			simu.setTitle("float");
			simu.setWindowedMode(1600, 1000);
			var asdf = new GUIView();
			asdf.addMM(mm);
			new Lwjgl3Application(asdf, simu);
		}
	}
}