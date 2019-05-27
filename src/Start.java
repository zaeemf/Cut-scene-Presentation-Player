import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.swing.JOptionPane;

public class Start {

	public static PlayerWindow vWindow;
	public static PlayerPanel vPlayer;

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub
		try {
			//InputStream scriptFile = Start.class.getResourceAsStream("/Resources/Scripts/Scene1.txt");
			//FileInputStream scriptFiles = Start.class.
			File scriptFile = new File(Start.class.getResource("/Resources/Scripts/Scene1.txt").getFile());
			//vPlayer = new PlayerPanel(script, "Scene1");
			vPlayer = new PlayerPanel();
			vWindow = new PlayerWindow(vPlayer);

			vPlayer.setScript(scriptFile);
			vPlayer.setScriptName("Scene1");
			vPlayer.startThread();
		} catch (Throwable t) {
			JOptionPane.showMessageDialog(
					null, t.getClass().getSimpleName() + ": " + t.getMessage());
			throw t; // don't suppress Throwable
		}
	}

}


