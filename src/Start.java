import java.io.File;

public class Start {
	
	public static PlayerWindow vWindow;
	public static PlayerPanel vPlayer;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File script = new File(Start.class.getResource("/Resources/Scripts/Scene1.txt").getFile());
		//vPlayer = new PlayerPanel(script, "Scene1");
		vPlayer = new PlayerPanel();
		vWindow = new PlayerWindow(vPlayer);
		
		vPlayer.setScript(script);
		vPlayer.setScriptName("Scene1");
		vPlayer.startThread();
	}

}
