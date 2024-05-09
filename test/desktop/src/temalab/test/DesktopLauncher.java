package temalab.test;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import temalab.common.MainModel;
import temalab.communicator.MainCommunicator;
import temalab.gui.view.GUIView;

public class DesktopLauncher {
	public static void main (String[] arg) {
		MainModel mm = new MainModel(60);
		MainCommunicator mc = new MainCommunicator(mm);
		if(arg[0].equals("graf")) {
			Lwjgl3ApplicationConfiguration simu = new Lwjgl3ApplicationConfiguration();
			simu.setTitle("float");
			simu.setWindowedMode(1600, 1000);
			var gv = new GUIView();
			gv.init(mm, mc, 1.1f, false);
			new Lwjgl3Application(gv, simu);
		}
	}
}